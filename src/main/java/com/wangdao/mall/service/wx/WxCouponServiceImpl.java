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
import com.wangdao.mall.mapper.CartDOMapper;
import com.wangdao.mall.mapper.CouponDOMapper;
import com.wangdao.mall.mapper.CouponUserDOMapper;
import com.wangdao.mall.mapper.GrouponRulesDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class WxCouponServiceImpl implements WxCouponService {

    @Autowired
    CouponDOMapper couponDOMapper;

    @Autowired
    CouponUserDOMapper couponUserDOMapper;

    @Autowired
    CartDOMapper cartDOMapper;

    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;

    /**
     * 优惠券列表  (每页只显示10张优惠券)(有desc关键字)(endTime对比new Date，过期了则不显示)
     * (total 优惠券数量 如果优惠券数量小于0则不显示)
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
                if (couponDO.getTotal()>=0) {  //如果total大于0,表示还有优惠券可以被领取
                    if (couponDO.getEndTime() != null) {    //有设置优惠券有效时间应该检查是否过期
                        if (!(new Date().after(couponDO.getEndTime()))) {  //没过期
                            data.add(couponDO);
                        }else {
                            couponDO.setStatus(Short.valueOf(String.valueOf(2)));
                            couponDO.setUpdateTime(new Date());
                            int i = couponDOMapper.updateByPrimaryKey(couponDO);//过期了得改掉status为2
                        }
                    } else {  //没有设置优惠券有效时间，直接显示
                        data.add(couponDO);
                    }
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
     *
     * (limit 用户领券限制数量，如果是0，则是不限制；默认是n，限领n张(通过coupon_id查询用户有次优惠券的数量，以便于限制领取次数))
     *
     * (total 优惠券数量 如果优惠券可被领取数量为0可以无限制领取，否则每领取一张，需要减少一张可领取数量)
     *
     * (type 优惠券赠送类型，如果是0则通用券，用户领取；如果是1，则是注册赠券；如果是2，则是优惠券码兑换)
     *
     * (time_type 有效时间限制，如果是0，则基于领取时间的有效天数days；如果是1，则start_time和end_time是优惠券有效期；)
     * (days  基于领取时间的有效天数days)  (如果是此属性，领取优惠券时需要手动计算设置CouponUserDO对象的StartTime和setEndTime)
     * @param couponId
     * @return
     */
    @Override
    public int couponReceive(Integer couponId) {
        Integer i = 0;
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //先获取将要领取此优惠券的对象
        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponId);

        if (couponDO.getTotal()<0){  //可被领取数量不足，不能领取
            return 0;
        }

        if (couponDO.getType()==2){  //如果是2，只能通过优惠券码兑换
            return 0;
        }else if (couponDO.getType()==1){  //如果是1，则是注册新用户才能领取赠券
            //先判断是否为新用户身份
            Calendar ca = Calendar.getInstance();
            ca.setTime(userDO.getAddTime());
            ca.add(Calendar.DATE, 7);  //设定新用户身份为7天

            System.out.println("当前时间"+new Date());
            System.out.println("新用户身份过期时间"+ca.getTime());
            if ((new Date().after(ca.getTime()))) {  //过期了
                return 0;
            }
        }

        if (couponDO.getLimit()>0){  //限制领取数量Limit
            //通过coupon_id查询用户有效 CouponUserDO 优惠券的数量，以便于限制领取limit数量
            CouponUserDOExample couponUserDOExample1 = new CouponUserDOExample();
            couponUserDOExample1.createCriteria().andDeletedEqualTo(false).andCouponIdEqualTo(couponId).andUserIdEqualTo(userDO.getId()).andStatusEqualTo(Short.valueOf(String.valueOf(0)));
            List<CouponUserDO> couponUserDOList1 = couponUserDOMapper.selectByExample(couponUserDOExample1);
            ArrayList<CouponUserDO> couponUserDOArrayList3 = new ArrayList<>();
            for (CouponUserDO couponUserDO : couponUserDOList1) {
                if (couponUserDO.getEndTime()!=null) {
                    if (!(new Date().after(couponUserDO.getEndTime()))) {  //没过期
                        couponUserDOArrayList3.add(couponUserDO);
                    } else {
                        couponUserDO.setStatus(Short.valueOf(String.valueOf(2)));
                        couponUserDO.setUpdateTime(new Date());
                        int i1 = couponUserDOMapper.updateByPrimaryKey(couponUserDO); //过期了得改掉status为2
                    }
                }
            }

            if (couponUserDOArrayList3.size()>=couponDO.getLimit()){  //用户最大化领取够了这张券，不能再领取了
                return 0;
            }
        }

        CouponUserDO couponUserDO = new CouponUserDO();
        couponUserDO.setUserId(userDO.getId());
        couponUserDO.setCouponId(couponId);
        couponUserDO.setStatus(couponDO.getStatus());

        if (couponDO.getTimeType()==1) {   //如果TimeType是1，则start_time和end_time是直接赋值优惠券couponDO的属性
            couponUserDO.setStartTime(couponDO.getStartTime());
            couponUserDO.setEndTime(couponDO.getEndTime());
        }else if (couponDO.getDays()!=null){  //如果TimeType是0，则start_time是new Date(),end_time是new Date() 加上days
            Calendar ca = Calendar.getInstance();
            ca.setTime(new Date());
            couponUserDO.setStartTime(ca.getTime());
            ca.add(Calendar.DATE, couponDO.getDays());
            couponUserDO.setEndTime(ca.getTime());
        }

        couponUserDO.setAddTime(new Date());
        couponUserDO.setUpdateTime(new Date());
        couponUserDO.setDeleted(false);

        //使用 Selective 插入，对象某属性没赋值或者为null时，不会把这个属性插入
        i=couponUserDOMapper.insertSelective(couponUserDO);
        if (couponDO.getTotal()>0 && i==1){  //领取成功，原优惠券可被领取数量减1
            couponDO.setTotal(couponDO.getTotal()-1);
            if (couponDO.getTotal()==0){   //先减少优惠券数量，如果减到最后为0，需要赋值为-1
                couponDO.setTotal(-1);
            }
            int j = couponDOMapper.updateByPrimaryKey(couponDO); //更新优惠券可领取数量状态
        }
        return i;
    }




    /**
     * 兑换优惠券
     * 找到code对的对象，判断 total 可被领取数量(领取了要减1);判断delete; 判断 limit 每人可领取数量; 才能领取
     * status: 使用状态, 如果是0则未使用；如果是1则已使用；如果是2则已过期；如果是3则已经下架,(使用了就改为1)；
     * @param code  兑换码
     * @return
     */
    @Override
    public Integer couponExchange(String code) {
        Integer i = 0;
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //先获取将要领取此优惠券的对象
        CouponDOExample couponDOExample1 = new CouponDOExample();
        couponDOExample1.createCriteria().andCodeEqualTo(code);
        List<CouponDO> couponDOList = couponDOMapper.selectByExample(couponDOExample1);

        if (couponDOList.size()==0){ //查无此优惠券
            return 0;
        }

        for (CouponDO couponDO : couponDOList) {
            if (couponDO.getTotal()<0){  //优惠券都被领取光了，无法领取
                return 0;
            }

            if (couponDO.getDeleted()){  //此优惠券已经逻辑删除，无法领取
                return 0;
            }

            if (couponDO.getLimit()>0){  //限制领取数量Limit
                //通过coupon_id查询用户有效 CouponUserDO 优惠券的数量，以便于限制领取limit数量
                CouponUserDOExample couponUserDOExample1 = new CouponUserDOExample();
                couponUserDOExample1.createCriteria().andDeletedEqualTo(false).andCouponIdEqualTo(couponDO.getId()).andUserIdEqualTo(userDO.getId()).andStatusEqualTo(Short.valueOf(String.valueOf(0)));
                List<CouponUserDO> couponUserDOList1 = couponUserDOMapper.selectByExample(couponUserDOExample1);
                ArrayList<CouponUserDO> couponUserDOArrayList = new ArrayList<>();
                for (CouponUserDO couponUserDO : couponUserDOList1) {
                    if (couponUserDO.getEndTime()!=null) {
                        if (!(new Date().after(couponUserDO.getEndTime()))) {  //没过期
                            couponUserDOArrayList.add(couponUserDO);
                        } else {
                            couponUserDO.setStatus(Short.valueOf(String.valueOf(2)));
                            couponUserDO.setUpdateTime(new Date());
                            int i1 = couponUserDOMapper.updateByPrimaryKey(couponUserDO); //过期了得改掉status为2
                        }
                    }
                }

                if (couponUserDOArrayList.size()>=couponDO.getLimit()){  //用户最大化领取够了这张券，不能再领取了
                    return 0;
                }
            }

            CouponUserDO couponUserDO = new CouponUserDO();
            couponUserDO.setUserId(userDO.getId());
            couponUserDO.setCouponId(couponDO.getId());
            couponUserDO.setStatus(couponDO.getStatus());

            if (couponDO.getTimeType()==1) {   //如果TimeType是1，则start_time和end_time是直接赋值优惠券couponDO的属性
                couponUserDO.setStartTime(couponDO.getStartTime());
                couponUserDO.setEndTime(couponDO.getEndTime());
            }else if (couponDO.getDays()!=null){  //如果TimeType是0，则start_time是new Date(),end_time是new Date() 加上days
                Calendar ca = Calendar.getInstance();
                ca.setTime(new Date());
                couponUserDO.setStartTime(ca.getTime());
                ca.add(Calendar.DATE, couponDO.getDays());
                couponUserDO.setEndTime(ca.getTime());
            }

            couponUserDO.setAddTime(new Date());
            couponUserDO.setUpdateTime(new Date());
            couponUserDO.setDeleted(false);

            //使用 Selective 插入，对象某属性没赋值或者为null时，不会把这个属性插入
            i=couponUserDOMapper.insertSelective(couponUserDO);
            if (couponDO.getTotal()>0 && i==1){  //领取成功，原优惠券可被领取数量减1
                couponDO.setTotal(couponDO.getTotal()-1);
                if (couponDO.getTotal()==0){   //先减少优惠券数量，如果减到最后为0，需要赋值为-1
                    couponDO.setTotal(-1);
                }
                int j = couponDOMapper.updateByPrimaryKey(couponDO); //更新优惠券可领取数量状态
            }
        }
        return i;
    }



    /**
     * status为0，但是已经过期;需要改进
     *
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
                            }else {
                                couponUserDO.setStatus(Short.valueOf(String.valueOf(2)));
                                couponUserDO.setUpdateTime(new Date());
                                int updateStatusNum = couponUserDOMapper.updateByPrimaryKey(couponUserDO); //过期了得改掉status为2
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


    /**
     * 当前用户 当前订单可用优惠券列表  只做goods_type==0
     * goods_type  商品限制类型，如果0则全商品，如果是1则是类目限制，如果是2则是商品限制。
     * goods_value 商品限制值，goods_type如果是0则空集合，如果是1则是类目集合，如果是2则是商品集合。
     * 找出符合 goods_type 的 status是0 的 delete为false 的 car总金额达到满减条件的 优惠券
     *
     * 先获取我的user_ID优惠券里 未用status==0 存在的delete==false 可用的start_time开始时间大于当前 未过期的当前时间大于end_time
     * 再获取: 通用goods_type==0优惠券，如果购物车总价格达到满减条件，返回此优惠券  (min 最少消费金额才能使用优惠券。)
     *
     * checked 购物车中商品是否选择状态
     * @param cartId
     * @param grouponRulesId
     * @return
     */
    @Override
    public List<Map> couponSelectlist(Integer cartId, Integer grouponRulesId) {
        ArrayList<Map> mapArrayList = new ArrayList<>();
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //获取user_ID 下 cart里所有商品总价格,每条购物车商品价格= price * number
        Double cartPrice=0.0;
        if (cartId>0) {
            CartDO cartDO = cartDOMapper.selectByPrimaryKey(cartId);
            cartPrice = (cartDO.getNumber() * cartDO.getPrice().doubleValue());
        }else {
            CartDOExample cartDOExample = new CartDOExample();
            cartDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andCheckedEqualTo(true);
            List<CartDO> cartDOS = cartDOMapper.selectByExample(cartDOExample);
            for (CartDO cartDO : cartDOS) {
                cartPrice = cartPrice + (cartDO.getNumber() * cartDO.getPrice().doubleValue());
            }
        }

        Double grouponRulesDiscount=0.0;
        if (grouponRulesId>0) {
            GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponRulesId);
            grouponRulesDiscount = grouponRulesDO.getDiscount().doubleValue();
        }

        ArrayList<CouponUserDO> couponUserDOArrayList = new ArrayList<>();
        //先获取我的user_ID优惠券里 未用status==0 存在的delete==false
        CouponUserDOExample couponUserDOExample = new CouponUserDOExample();
        couponUserDOExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId()).andStatusEqualTo(Short.valueOf(String.valueOf(0)));
        List<CouponUserDO> couponUserDOList = couponUserDOMapper.selectByExample(couponUserDOExample);
        for (CouponUserDO couponUserDO : couponUserDOList) {
            if (couponUserDO.getStartTime()!=null && couponUserDO.getEndTime()!=null){
                if (!(new Date().after(couponUserDO.getEndTime())) && new Date().after(couponUserDO.getStartTime()) ) {  //没过期
                    couponUserDOArrayList.add(couponUserDO);//可用的start_time开始时间大于当前 未过期的当前时间大于end_time
                }
            }
        }

        for (CouponUserDO couponUserDO : couponUserDOArrayList) {
            CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponUserDO.getCouponId());
            if (couponDO!=null){
                Double v = cartPrice - grouponRulesDiscount;
                if (v >= (couponDO.getMin()).doubleValue()){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id",couponUserDO.getId());
                    map.put("name",couponDO.getName());
                    map.put("desc",couponDO.getDesc());
                    map.put("tag",couponDO.getTag());
                    map.put("min",couponDO.getMin());
                    map.put("discount",couponDO.getDiscount());
                    map.put("startTime",couponUserDO.getStartTime());
                    map.put("endTime",couponUserDO.getEndTime());
                    mapArrayList.add(map);
                }
            }
        }

        return mapArrayList;
    }
}
