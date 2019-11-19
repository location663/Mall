//package com.wangdao.mall.service.wx;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wangdao.mall.bean.*;
//import com.wangdao.mall.mapper.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@Service
//public class WxOrderServiceImpl implements WxOrderService {
//
//    @Autowired
//    GoodsProductDOMapper goodsProductDOMapper;
//
//    @Autowired
//    OrderGoodsDOMapper orderGoodsDOMapper;
//
//    @Autowired
//    OrderDOMapper orderDOMapper;
//
//    @Autowired
//    CommentDOMapper commentDOMapper;
//
//    @Autowired
//    SystemDOMapper systemDOMapper;
//
//    @Autowired
//    AddressDOMapper addressDOMapper;
//
//    @Autowired
//    CartDOMapper cartDOMapper;
//
//    @Autowired
//    CouponDOMapper couponDOMapper;
//
//    @Autowired
//    GrouponDOMapper grouponDOMapper;
//
//    @Autowired
//    GrouponRulesDOMapper grouponRulesDOMapper;
//
//    /**
//     * 提交订单
//     * @param map
//     * @return
//     */
//    @Override
//    public BaseReqVo submitOrder(Map<String, Object> map) throws JsonProcessingException {
//
//        OrderDO orderDO = new OrderDO();
//
//        //订单状态
//        orderDO.setOrderStatus((short) 201);
//
//        //用户积分
//        orderDO.setIntegralPrice(BigDecimal.valueOf(0));
//
//        //收货人信息
//        Integer addressId = (Integer) map.get("addressId");
//        AddressDO addressDO = addressDOMapper.selectByPrimaryKey(addressId);
//        //用户id
//        orderDO.setUserId(addressDO.getUserId());
//        //收货人姓名
//        orderDO.setConsignee(addressDO.getName());
//        //收货人手机号码
//        orderDO.setMobile(addressDO.getMobile());
//        //收货人地址
//        orderDO.setAddress(addressDO.getAddress());
//
//        //购物车信息
//        Integer cartId = (Integer) map.get("cartId");
//        CartDO cartDO = cartDOMapper.selectByPrimaryKey(cartId);
//        //商品总费用
//        orderDO.setGoodsPrice(cartDO.getPrice());
//
//        //配送费用
//        SystemDO systemDOMin = systemDOMapper.selectByPrimaryKey(5);
//        String keyValue = systemDOMin.getKeyValue();
//        if (orderDO.getGoodsPrice().doubleValue() > Integer.valueOf(keyValue)){
//            orderDO.setFreightPrice(BigDecimal.valueOf(0));
//        }else {
//            SystemDO systemDOValue = systemDOMapper.selectByPrimaryKey(7);
//            orderDO.setFreightPrice(BigDecimal.valueOf(Integer.valueOf(systemDOValue.getKeyValue())));
//        }
////        orderDO.setFreightPrice(BigDecimal.valueOf(10));
//
//        //待评价订单商品数量
//        CommentDOExample commentDOExample = new CommentDOExample();
//        commentDOExample.createCriteria().andValueIdEqualTo(cartDO.getGoodsId()).andDeletedEqualTo(false);
//        List<CommentDO> commentDOList = commentDOMapper.selectByExample(commentDOExample);
//        if (commentDOList == null){
//            orderDO.setComments((short) 1);
//        }else {
//            orderDO.setComments((short) 0);
//        }
//
//        //优惠券信息
//        Integer couponId = (Integer) map.get("couponId");
//        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponId);
//        if (couponDO != null) {
//            if (cartDO.getPrice().doubleValue() - couponDO.getMin().doubleValue() >= 0) {
//                //优惠券优惠价格
//                orderDO.setCouponPrice(couponDO.getDiscount());
//            }
//        }
//
//        //团购联系信息
//        Integer grouponLinkId = (Integer) map.get("grouponLinkId");
//        GrouponDO grouponDO = grouponDOMapper.selectByPrimaryKey(grouponLinkId);
//
//
//
//        //团购规则信息
//        Integer grouponRulesId = (Integer) map.get("grouponRulesId");
//        GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponRulesId);
//        //团购优惠价
//        orderDO.setGrouponPrice(grouponRulesDO.getDiscount());
//        if (new Date().after(grouponRulesDO.getExpireTime())){
//            return new BaseReqVo(null,"团购活动已过期",730);
//        }
//
//        //订单付款时留言
//        String message = (String) map.get("message");
//        orderDO.setMessage(message);
//
//        //订单费用
//        double orderPrice = orderDO.getGoodsPrice().doubleValue()+orderDO.getFreightPrice().doubleValue()-orderDO.getCouponPrice().doubleValue();
//        orderDO.setOrderPrice(BigDecimal.valueOf(orderPrice));
//
//        //实付费用
//        double actualPrice = orderDO.getOrderPrice().doubleValue()- orderDO.getIntegralPrice().doubleValue();
//        orderDO.setActualPrice(BigDecimal.valueOf(actualPrice));
//
//        //微信付款时间
//        orderDO.setPayTime(new Date());
//
//        //发货快递公司
//        orderDO.setShipChannel("顺丰");
//
//        //订单关闭时间
//        SystemDO systemDO = systemDOMapper.selectByPrimaryKey(1);
//        String orderUnpaid = systemDO.getKeyValue();
//
//        Date date = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.MINUTE, Integer.valueOf(orderUnpaid));
//        date = c.getTime();
//        orderDO.setEndTime(date);
//
//        //创建时间
//        orderDO.setAddTime(new Date());
//
//        //更新时间
//        orderDO.setUpdateTime(new Date());
//
//        //逻辑删除
//        orderDO.setDeleted(false);
//
//        //插入order数据
//        orderDOMapper.insertSelective(orderDO);
//
//        //更改订单编号、微信付款编号和发货编号
//        int i = orderDOMapper.selectLastInsertId();
//        orderDO.setOrderSn(String.valueOf(i));
//        orderDO.setPayId(String.valueOf(i));
//        orderDO.setShipSn(String.valueOf(i));
//        orderDOMapper.updateByPrimaryKeySelective(orderDO);
//
//        //order_goods表订单id
//        OrderGoodsDO orderGoodsDO = new OrderGoodsDO();
//        orderGoodsDO.setOrderId(i);
//
//        //order_goods表商品id
//        Integer goodsId = cartDO.getGoodsId();
//        orderGoodsDO.setGoodsId(goodsId);
//
//        //order_goods表商品名称
//        String goodsName = cartDO.getGoodsName();
//        orderGoodsDO.setGoodsName(goodsName);
//
//        //order_goods表商品编号
//        String goodsSn = cartDO.getGoodsSn();
//        orderGoodsDO.setGoodsSn(goodsSn);
//
//        //order_goods表商品货品的id
//        GoodsProductDOExample goodsProductDOExample = new GoodsProductDOExample();
//        goodsProductDOExample.createCriteria().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
//        List<GoodsProductDO> goodsProductDOS = goodsProductDOMapper.selectByExample(goodsProductDOExample);
//        GoodsProductDO goodsProductDO = goodsProductDOS.get(0);
//        orderGoodsDO.setProductId(goodsProductDO.getId());
//
//        //order_goods表商品货品的购买数量
//        orderGoodsDO.setNumber(goodsProductDO.getNumber());
//
//        //order_goods表商品货品的售价
//        orderGoodsDO.setPrice(goodsProductDO.getPrice());
//
//        //order_goods表商品货品的规格列表
//        ObjectMapper objectMapper = new ObjectMapper();
//        String string = objectMapper.writeValueAsString(goodsProductDO.getSpecifications());
//        orderGoodsDO.setSpecifications(string);
//
//        //order_goods表商品货品的图片
//        orderGoodsDO.setPicUrl(goodsProductDO.getUrl());
//        CommentDO commentDO = commentDOList.get(0);
//        orderGoodsDO.setComment(commentDO.getType());
//
//        //order_goods表创建时间
//        orderGoodsDO.setAddTime(new Date());
//
//        //order_goods表更新时间
//        orderGoodsDO.setUpdateTime(new Date());
//
//        //逻辑删除
//        orderGoodsDO.setDeleted(false);
//
//        Map<String, Object> dateMap = new HashMap<>();
//        dateMap.put("orderId",i);
//        return new BaseReqVo(dateMap,"成功",0);
//    }
//}
