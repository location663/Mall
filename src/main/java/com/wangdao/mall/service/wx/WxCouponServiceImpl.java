/**
 * Created by IntelliJ IDEA.
 * User: Jql
 * Date  2019/11/20
 * Time  下午 8:04
 */

package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.CouponDO;
import com.wangdao.mall.bean.CouponDOExample;
import com.wangdao.mall.bean.GoodsDO;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.mapper.CouponDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WxCouponServiceImpl implements WxCouponService {

    @Autowired
    CouponDOMapper couponDOMapper;


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
     * 领取优惠券
     * @param map
     * @return
     */
    @Override
    public int couponReceive(Map map) {
        //获取当前用户登录对象
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        return 0;
    }
}
