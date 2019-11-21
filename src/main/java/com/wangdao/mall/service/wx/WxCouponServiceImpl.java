/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/20
 * Time  下午 8:04
 */

package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.CouponDOMapper;
import com.wangdao.mall.mapper.CouponUserDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WxCouponServiceImpl implements WxCouponService {

    @Autowired
    CouponDOMapper couponDOMapper;

    @Autowired
    CouponUserDOMapper couponUserDOMapper;


    /**
     * 优惠券列表  (每页只显示10张优惠券)(有desc关键字)(endTime对比new Date，过期了则不显示)
     * @param page
     * @param size
     * @return
     */
    @Override
    public HashMap<String, Object> queryCouponList(Integer page, Integer size) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<CouponDO> data = new ArrayList<>();
        PageHelper.startPage(page,size);
        CouponDOExample couponDOExample = new CouponDOExample();
        couponDOExample.createCriteria().andDeletedEqualTo(false);
        List<CouponDO> couponDOList = couponDOMapper.selectByExample(couponDOExample);
        if (couponDOList.size()>0) {
            for (CouponDO couponDO : couponDOList) {
                if (couponDO.getEndTime()!=null) {    //有设置优惠券有效时间应该检查是否过期
                    if (!(new Date().after(couponDO.getEndTime()))) {  //没过期
                        data.add(couponDO);
                    }
                }else {  //没有设置优惠券有效时间，直接显示
                    data.add(couponDO);
                }
            }
        }

        PageInfo<CouponDO> userPageInfo = new PageInfo<>(data);
        long total = userPageInfo.getTotal();
        map.put("data",data);
        map.put("count",total);

        return map;
    }


    /**
     * 领取优惠券  需要改进
     * (used_time和order_id只在已经使用的优惠券中显示，新领取优惠券时此两个属性应该为null,改变使用状态应该在下单操作里)
     * (add_time和update_time应该及时更新，add_time在领取时创建，update_time在优惠券删除，过期，或者使用后更新)
     * (limit 用户领券限制数量，如果是0，则是不限制；默认是1，限领一张(通过coupon_id查询用户有次优惠券的数量，以便于限制领取次数))
     * (type 优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换)
     * (time_type 有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期；)
     * (days  基于领取时间的有效天数days)  (如果是此属性，领取优惠券时需要手动计算设置CouponUserDO对象的StartTime和setEndTime)
     * @param couponId
     * @return
     */
    @Override
    public int couponReceive(Integer couponId) {
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //先获取此优惠券的对象
        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponId);

        CouponUserDO couponUserDO = new CouponUserDO();
        couponUserDO.setUserId(userDO.getId());
        couponUserDO.setCouponId(couponId);
        couponUserDO.setStatus(couponDO.getStatus());
        couponUserDO.setStartTime(couponDO.getStartTime());
        couponUserDO.setEndTime(couponDO.getEndTime());
        couponUserDO.setAddTime(new Date());
        couponUserDO.setUpdateTime(new Date());
        couponUserDO.setDeleted(false);
        Integer i = 0;
        //使用 Selective 插入，对象某属性没赋值或者为null时，不会把这个属性插入
        i=couponUserDOMapper.insertSelective(couponUserDO);
        return i;
    }



    /**
     * 获取我的优惠券列表
     * (type 优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换)
     *
     * 如果下单时会使用优惠券，记得改变优惠券的某些属性，"我的优惠券"存在名为 coupon_user 的数据表里，
     * update_time
     * status: 使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架,(使用了就改为1)；
     * used_time  :使用了此优惠券的时间(下单时间)
     * order_id :使用了此优惠券的订单号id
     * @param status
     * @param page
     * @param size
     * @return  其中的"count": 23  //好像是优惠券总数，无论使用，过期状态，无论是否逻辑删除删除
     */
    @Override
    public HashMap<String, Object> couponMylist(Short status, Integer page, Integer size) {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Map> data = new ArrayList<>();
        PageHelper.startPage(page,size);
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();
        CouponUserDOExample couponUserDOExample = new CouponUserDOExample();

        //先获取该用户在某 status 状态下，endtime不晚于当前时间下，delete为falues下,user_id下 的我的优惠券的coupon_id
        if (status==0 || status==1){  //未使用状态和已使用状态
            couponUserDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andStatusEqualTo(status);
            List<CouponUserDO> couponUserDOList = couponUserDOMapper.selectByExample(couponUserDOExample);
            ArrayList<CouponUserDO> couponUserDOArrayList = new ArrayList<>();

            if (couponUserDOList.size()>0) {
                if (status==0) {  //未使用状态
                    for (CouponUserDO couponUserDO : couponUserDOList) {
                        if (couponUserDO.getEndTime() != null) {    //有设置优惠券有效时间应该检查是否过期
                            if (!(new Date().after(couponUserDO.getEndTime()))) {  //没过期
                                couponUserDOArrayList.add(couponUserDO);
                            }
                        } else {  //没有设置优惠券有效时间，直接显示
                            couponUserDOArrayList.add(couponUserDO);
                        }
                    }
                    if (couponUserDOArrayList.size() > 0) {
                        for (CouponUserDO couponUserDO : couponUserDOArrayList) {
                            HashMap<String, Object> map1 = new HashMap<>();
                            //通过coupon_id获取优惠券CouponDO的详细对象属性
                            CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponUserDO.getCouponId());
                            map1.put("id", couponUserDO.getCouponId());  //好像是coupon_id  老师项目请求里返回此属性有重复现象
                            map1.put("name", couponDO.getName());
                            map1.put("desc", couponDO.getDesc());
                            map1.put("tag", couponDO.getTag());
                            map1.put("min", couponDO.getMin());
                            map1.put("discount", couponDO.getDiscount());
                            map1.put("startTime", couponUserDO.getStartTime());  // coupon_user 数据表里的属性
                            map1.put("endTime", couponUserDO.getEndTime());      // coupon_user 数据表里的属性
                            data.add(map1);
                        }
                        map.put("data", data);
                    }
                }else{  //status=1 已使用状态
                    for (CouponUserDO couponUserDO : couponUserDOList) {
                        HashMap<String, Object> map1 = new HashMap<>();
                        //通过coupon_id获取优惠券CouponDO的详细对象属性
                        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponUserDO.getCouponId());
                        map1.put("id", couponUserDO.getCouponId());  //好像是coupon_id  老师项目请求里返回此属性有重复现象
                        map1.put("name", couponDO.getName());
                        map1.put("desc", couponDO.getDesc());
                        map1.put("tag", couponDO.getTag());
                        map1.put("min", couponDO.getMin());
                        map1.put("discount", couponDO.getDiscount());
                        map1.put("startTime", couponUserDO.getStartTime());  // coupon_user 数据表里的属性
                        map1.put("endTime", couponUserDO.getEndTime());      // coupon_user 数据表里的属性
                        data.add(map1);
                    }
                    map.put("data", data);
                }
            }
        } else if (status==2){
            couponUserDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andStatusEqualTo(status);
            List<CouponUserDO> couponUserDOList = couponUserDOMapper.selectByExample(couponUserDOExample);
            if (couponUserDOList.size()>0) {
                for (CouponUserDO couponUserDO : couponUserDOList) {
                    HashMap<String, Object> map1 = new HashMap<>();
                    //通过coupon_id获取优惠券CouponDO的详细对象属性
                    CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponUserDO.getCouponId());
                    map1.put("id", couponUserDO.getCouponId());  //好像是coupon_id  老师项目请求里返回此属性有重复现象
                    map1.put("name", couponDO.getName());
                    map1.put("desc", couponDO.getDesc());
                    map1.put("tag", couponDO.getTag());
                    map1.put("min", couponDO.getMin());
                    map1.put("discount", couponDO.getDiscount());
                    map1.put("startTime", couponUserDO.getStartTime());  // coupon_user 数据表里的属性
                    map1.put("endTime", couponUserDO.getEndTime());      // coupon_user 数据表里的属性
                    data.add(map1);
                }
                map.put("data", data);
            }
        }

        //封装 count 好像是该用户所有优惠券总数，无论使用，过期状态，无论是否逻辑删除删除
        CouponUserDOExample couponUserDOExample1 = new CouponUserDOExample();
        couponUserDOExample1.createCriteria().andUserIdEqualTo(userDO.getId());
        List<CouponUserDO> couponUserDOList = couponUserDOMapper.selectByExample(couponUserDOExample1);
        PageInfo<CouponUserDO> userPageInfo = new PageInfo<>(couponUserDOList);
        long total = userPageInfo.getTotal();
        map.put("count", total);

        return map;
    }
}