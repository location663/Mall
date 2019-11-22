package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.CouponDO;
import com.wangdao.mall.bean.CouponDOExample;
import com.wangdao.mall.bean.CouponUserDO;
import com.wangdao.mall.bean.CouponUserDOExample;
import com.wangdao.mall.mapper.CouponDOMapper;
import com.wangdao.mall.mapper.CouponUserDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-16 16:29
 **/
@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponDOMapper couponDOMapper;

    @Autowired
    CouponUserDOMapper couponUserDOMapper;


    /**逻辑删除， 其实是将deleted更新为true
     * 考虑要不要修改coupon_user的优惠券的对应信息~~~~~~~
     * @param id
     * @return
     */
    @Override
    public int deleteCoupon(Integer id) {
        int update = couponDOMapper.deleteCouponById(id);
        return update;
    }

    /**编辑优惠券
     * @param couponDO
     * @return
     */
    @Override
    public CouponDO updateCoupon(CouponDO couponDO) {
        //如果优惠券类型不再是兑换码, 应该把code置空，如果以前不是兑换码，现在改为兑换码，应该生成一个兑换码
        if(couponDO.getType() != 2){
            couponDO.setCode("");
        }else{
            int i1 = new Random().nextInt(100) + couponDO.getName().hashCode();
            String s = Integer.toHexString(i1).toUpperCase();
            couponDO.setCode(s);
        }
        couponDOMapper.updateByPrimaryKey(couponDO);
        //应该给一个返回值，并写一个判断，更新成功再做下面的事情，不然抛一个异常
        CouponDO couponDO1 = couponDOMapper.selectByPrimaryKey(couponDO.getId());
        return couponDO1;
    }

    /**优惠券详情里的查询优惠券-用户信息
     * @param page
     * @param limit
     * @param couponId
     * @param userId
     * @param status
     * @return
     */
    @Override
    public Map<String, Object> queryCouponsByConditions(Integer page, Integer limit, Integer couponId, Integer userId, Integer status) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page, limit);
        CouponUserDOExample couponUserDOExample = new CouponUserDOExample();
        CouponUserDOExample.Criteria criteria = couponUserDOExample.createCriteria();
        if(userId != null)
            criteria.andUserIdEqualTo(userId);
        if(status != null)
            criteria.andStatusEqualTo(status.shortValue());
        criteria.andDeletedEqualTo(false);
        criteria.andCouponIdEqualTo(couponId);
        List<CouponUserDO> couponUserDOList = couponUserDOMapper.selectByExample(couponUserDOExample);

        Map<String, Object> map = new HashMap<>();
        map.put("items", couponUserDOList);
        PageInfo<CouponUserDO> couponUserDOPageInfo = new PageInfo<>(couponUserDOList);
        long total = couponUserDOPageInfo.getTotal();
        System.out.println(total);
        map.put("total", total);
        return map;
    }

    /**新建优惠券时，根据插入数据库返回的id，查询优惠券
     * @param id
     * @return
     */
    @Override
    public CouponDO queryCouponByKey(int id) {
        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(id);
        return couponDO;
    }


    /**新建优惠券
     * @param couponDO
     * @return
     */
    @Override
    public int createCoupon(CouponDO couponDO) {
        Date date = new Date();
        couponDO.setAddTime(date);
        couponDO.setUpdateTime(date);
        int i = couponDOMapper.insertSelective(couponDO);
        int id = 0;
        if(i == 1) {
            id = couponDOMapper.selectLastInsertId();
        }
        if(couponDO.getStartTime() != null && couponDO.getEndTime() != null){
            couponDOMapper.updateDays(id);//应该用selectKey,在插入之前就先设置好其中的属性
        }
        if(couponDO.getType() == 2){//兑换码的生成是不是应该在前面插入优惠券的时候就直接设置好？？
            //生成一个随机的兑换码
            int i1 = new Random().nextInt(100) + couponDO.getName().hashCode();
            String s = Integer.toHexString(i1).toUpperCase();
            couponDOMapper.updateCode(id, s);
        }
        return id;
    }

    /**
     * 模糊查询优惠券
     * @param page
     * @param limit
     * @param name
     * @param type
     * @param status
     * @return
     */
    @Override
    public Map<String, Object> queryCoupons(Integer page, Integer limit, String name, Integer type, Integer status) {
        Map<String, Object> map = new HashMap<>();
        CouponDOExample couponDOExample = new CouponDOExample();
        couponDOExample.setOrderByClause("add_time desc");
        PageHelper.startPage(page, limit);
        CouponDOExample.Criteria criteria = couponDOExample.createCriteria();
        if(name != null){
            criteria.andNameLike("%" + name + "%");
        }
        if(type != null){
            criteria.andTypeEqualTo(type.shortValue());
        }
        if(status != null){
            criteria.andStatusEqualTo( status.shortValue());
        }
        criteria.andDeletedEqualTo(false);
        List<CouponDO> couponDOList = couponDOMapper.selectByExample(couponDOExample);
        map.put("items", couponDOList);

        PageInfo<Object> objectPageInfo = new PageInfo<>();
        long total = objectPageInfo.getTotal();
        map.put("total", total);
        return map;
    }
}
