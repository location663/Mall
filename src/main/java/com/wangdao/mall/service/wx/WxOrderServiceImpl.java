package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    GoodsProductDOMapper goodsProductDOMapper;

    @Autowired
    OrderGoodsDOMapper orderGoodsDOMapper;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    CommentDOMapper commentDOMapper;

    @Autowired
    SystemDOMapper systemDOMapper;

    @Autowired
    AddressDOMapper addressDOMapper;

    @Autowired
    CartDOMapper cartDOMapper;

    @Autowired
    CouponDOMapper couponDOMapper;

    @Autowired
    GrouponDOMapper grouponDOMapper;

    @Autowired
    GrouponRulesDOMapper grouponRulesDOMapper;

    /**
     * 提交订单
     * @param map
     * @return
     */
    @Override
    public BaseReqVo submitOrder(Map<String, Object> map) {

        OrderDO orderDO = new OrderDO();
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //订单状态
        orderDO.setOrderStatus((short) 201);

        //用户积分
        orderDO.setIntegralPrice(BigDecimal.valueOf(0));

        //收货人信息
        Integer addressId = (Integer) map.get("addressId");
        AddressDO addressDO = addressDOMapper.selectByPrimaryKey(addressId);
        //用户id
        orderDO.setUserId(addressDO.getUserId());
        //收货人姓名
        orderDO.setConsignee(addressDO.getName());
        //收货人手机号码
        orderDO.setMobile(addressDO.getMobile());
        //收货人地址
        orderDO.setAddress(addressDO.getAddress());

        //购物车信息
        Integer cartId = (Integer) map.get("cartId");
        List<CartDO> cartDOList = new ArrayList<>();
        if (cartId != 0) {
            CartDO cartDO = cartDOMapper.selectByPrimaryKey(cartId);
            cartDOList.add(cartDO);
        }else {
            CartDOExample cartDOExample = new CartDOExample();
            cartDOExample.createCriteria().andCheckedEqualTo(true).andDeletedEqualTo(false).andUserIdEqualTo(userDO.getId());
            cartDOList = cartDOMapper.selectByExample(cartDOExample);
            cartDOExample.clear();
        }
        //商品总费用
        double goodsPrice = 0;
        for (CartDO cartDO : cartDOList) {
            double cartPrice = cartDO.getPrice().doubleValue() * cartDO.getNumber();
            goodsPrice += cartPrice;
        }
        orderDO.setGoodsPrice(BigDecimal.valueOf(goodsPrice));

        //配送费用
        SystemDO systemDOMin = systemDOMapper.selectByPrimaryKey(5);
        String keyValue = systemDOMin.getKeyValue();
        if (orderDO.getGoodsPrice().doubleValue() > Integer.valueOf(keyValue)){
            orderDO.setFreightPrice(BigDecimal.valueOf(0));
        }else {
            SystemDO systemDOValue = systemDOMapper.selectByPrimaryKey(7);
            orderDO.setFreightPrice(BigDecimal.valueOf(Integer.valueOf(systemDOValue.getKeyValue())));
        }

        //待评价订单商品数量
        orderDO.setComments((short)cartDOList.size());
//        CommentDOExample commentDOExample = new CommentDOExample();
//        commentDOExample.createCriteria().andValueIdEqualTo(cartDO.getGoodsId()).andDeletedEqualTo(false);
//        List<CommentDO> commentDOList = commentDOMapper.selectByExample(commentDOExample);
//        if (commentDOList == null){
//            orderDO.setComments((short) 1);
//        }else {
//            orderDO.setComments((short) 0);
//        }

        //优惠券信息
        Integer couponId = (Integer) map.get("couponId");
        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponId);
        if (couponDO != null) {
            if (orderDO.getGoodsPrice().doubleValue() - couponDO.getMin().doubleValue() >= 0) {
                //优惠券优惠价格
                orderDO.setCouponPrice(couponDO.getDiscount());
            }
        }

        //团购联系信息
        Integer grouponLinkId = (Integer) map.get("grouponLinkId");
        GrouponDO grouponDO = grouponDOMapper.selectByPrimaryKey(grouponLinkId);

        //团购规则信息
        Integer grouponRulesId = (Integer) map.get("grouponRulesId");
        GrouponRulesDO grouponRulesDO = grouponRulesDOMapper.selectByPrimaryKey(grouponRulesId);
        //团购优惠价
        if (grouponRulesDO != null) {
            orderDO.setGrouponPrice(grouponRulesDO.getDiscount());
            if (new Date().after(grouponRulesDO.getExpireTime())) {
                return new BaseReqVo(null, "团购活动已过期", 730);
            }
        }

        //订单付款时留言
        String message = (String) map.get("message");
        orderDO.setMessage(message);

        //订单费用
        double orderPrice = orderDO.getGoodsPrice().doubleValue()+orderDO.getFreightPrice().doubleValue()-orderDO.getCouponPrice().doubleValue();
        orderDO.setOrderPrice(BigDecimal.valueOf(orderPrice));

        //实付费用
        double actualPrice = orderDO.getOrderPrice().doubleValue()- orderDO.getIntegralPrice().doubleValue();
        orderDO.setActualPrice(BigDecimal.valueOf(actualPrice));

        //微信付款时间
        orderDO.setPayTime(new Date());

        //发货快递公司
        orderDO.setShipChannel("顺丰");

        //订单关闭时间
        SystemDO systemDO = systemDOMapper.selectByPrimaryKey(1);
        String orderUnpaid = systemDO.getKeyValue();

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, Integer.valueOf(orderUnpaid));
        date = c.getTime();
        orderDO.setEndTime(date);

        //创建时间
        orderDO.setAddTime(new Date());

        //更新时间
        orderDO.setUpdateTime(new Date());

        //逻辑删除
        orderDO.setDeleted(false);

        //插入order数据
        orderDOMapper.insertSelective(orderDO);

        //更改订单编号、微信付款编号和发货编号
        int i = orderDOMapper.selectLastInsertId();
        orderDO.setOrderSn(String.valueOf(i));
        orderDO.setPayId(String.valueOf(i));
        orderDO.setShipSn(String.valueOf(i));
        orderDOMapper.updateByPrimaryKeySelective(orderDO);

        //order_goods表对象
        List<OrderGoodsDO> orderGoodsDOList = new ArrayList<>();
        for (CartDO cartDO : cartDOList) {

            //order_goods表订单id
            OrderGoodsDO orderGoodsDO = new OrderGoodsDO();
            orderGoodsDO.setOrderId(i);

            //order_goods表商品id
            Integer goodsId = cartDO.getGoodsId();
            orderGoodsDO.setGoodsId(goodsId);

            //order_goods表商品名称
            String goodsName = cartDO.getGoodsName();
            orderGoodsDO.setGoodsName(goodsName);

            //order_goods表商品编号
            String goodsSn = cartDO.getGoodsSn();
            orderGoodsDO.setGoodsSn(goodsSn);

            //order_goods表创建时间
            orderGoodsDO.setAddTime(new Date());

            //order_goods表更新时间
            orderGoodsDO.setUpdateTime(new Date());

            //逻辑删除
            orderGoodsDO.setDeleted(false);

            //插入order_goods表中
            orderGoodsDOMapper.insertSelective(orderGoodsDO);
        }

//        //order_goods表订单id
//        OrderGoodsDO orderGoodsDO = new OrderGoodsDO();
//        orderGoodsDO.setOrderId(i);

//        //order_goods表商品id
//        Integer goodsId = cartDO.getGoodsId();
//        orderGoodsDO.setGoodsId(goodsId);

//        //order_goods表商品名称
//        String goodsName = cartDO.getGoodsName();
//        orderGoodsDO.setGoodsName(goodsName);

//        //order_goods表商品编号
//        String goodsSn = cartDO.getGoodsSn();
//        orderGoodsDO.setGoodsSn(goodsSn);

        /*无法插入，后续再进行*/
        /*//order_goods表商品货品的id
        GoodsProductDOExample goodsProductDOExample = new GoodsProductDOExample();
        goodsProductDOExample.createCriteria().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
        List<GoodsProductDO> goodsProductDOS = goodsProductDOMapper.selectByExample(goodsProductDOExample);
        GoodsProductDO goodsProductDO = goodsProductDOS.get(0);
        orderGoodsDO.setProductId(goodsProductDO.getId());

        //order_goods表商品货品的购买数量
        int number = goodsProductDO.getNumber();
        orderGoodsDO.setNumber((short)number);

        //order_goods表商品货品的售价
        orderGoodsDO.setPrice(goodsProductDO.getPrice());

        //order_goods表商品货品的规格列表
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(goodsProductDO.getSpecifications());
        orderGoodsDO.setSpecifications(string);

        //order_goods表商品货品的图片
        orderGoodsDO.setPicUrl(goodsProductDO.getUrl());
        CommentDO commentDO = commentDOList.get(0);
        orderGoodsDO.setComment((int)commentDO.getType());*/

//        //order_goods表创建时间
//        orderGoodsDO.setAddTime(new Date());
//
//        //order_goods表更新时间
//        orderGoodsDO.setUpdateTime(new Date());
//
//        //逻辑删除
//        orderGoodsDO.setDeleted(false);

        //逻辑删除cart表中已被选中的购物车信息
        CartDO cartDO = new CartDO();
        cartDO.setDeleted(true);
        CartDOExample cartDOExample = new CartDOExample();
        cartDOExample.createCriteria().andDeletedEqualTo(false).andCheckedEqualTo(true);
        cartDOMapper.updateByExampleSelective(cartDO,cartDOExample);

        //返回结果
        Map<String, Object> dateMap = new HashMap<>();
        dateMap.put("orderId",i);
        return new BaseReqVo(dateMap,"成功",0);
    }

    /**
     * 根据订单状态获取对应订单列表
     * @param showType
     * @param page
     * @param size
     * @return
     */
    @Override
    public BaseReqVo getOrderList(Integer showType, Integer page, Integer size) {

        List<OrderVO> orderVOList = new ArrayList<>();
        int count = 0;

        //获取user对象
        UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //转换状态码
        int statuInt = 0;
        switch (showType){
            case 1:
                statuInt = 101;
                break;
            case 2:
                statuInt = 201;
                break;
            case 3:
                statuInt = 301;
                break;
            case 4:
                statuInt = 401;
                break;
            default:
                break;
        }

        //获取符合userId和订单状态条件的order
        List<OrderDO> orderDOList = new ArrayList<>();
        OrderDOExample orderDOExample = new OrderDOExample();
        if (showType != 0) {
            PageHelper.startPage(page,size);
            orderDOExample.createCriteria().andUserIdEqualTo(user.getId()).andOrderStatusEqualTo((short) statuInt).andDeletedEqualTo(false);
            orderDOList = orderDOMapper.selectByExample(orderDOExample);
            List<OrderDO> allOrderDOList = orderDOMapper.selectByExample(orderDOExample);
            count = allOrderDOList.size();
        }else {
            PageHelper.startPage(page,size);
            orderDOExample.createCriteria().andUserIdEqualTo(user.getId()).andDeletedEqualTo(false);
            orderDOList = orderDOMapper.selectByExample(orderDOExample);
            List<OrderDO> allOrderDOList = orderDOMapper.selectByExample(orderDOExample);
            count = allOrderDOList.size();
        }

        //获取orderOV对象
        for (OrderDO orderDO : orderDOList) {

            OrderVO orderVO = new OrderVO();

            //id
            orderVO.setId(orderDO.getId());

            //orderStatusText
            Integer orderStatus = Integer.valueOf(orderDO.getOrderStatus());
            String orderStatusText = null;
            switch (orderStatus){
                case 101:
                    orderStatusText = "未付款";
                    break;
                case 201:
                    orderStatusText = "已付款";
                    break;
                case 301:
                    orderStatusText = "已发货";
                    break;
                case 401:
                    orderStatusText = "已收货";
                    break;
            }
            orderVO.setOrderStatusText(orderStatusText);

            //isGroupin
            BigDecimal grouponPrice = orderDOMapper.selectByPrimaryKey(orderDO.getId()).getGrouponPrice();
            if (grouponPrice.doubleValue() != 0){
                orderVO.setIsGroupin(true);
            }

            //orderSn
            orderVO.setOrderSn(orderDO.getOrderSn());

            //actualPrice
            orderVO.setActualPrice(orderDO.getActualPrice().doubleValue());

            //goodsList
            OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
            orderGoodsDOExample.createCriteria().andGoodsIdEqualTo(orderDO.getId()).andDeletedEqualTo(false);
            List<OrderGoodsDO> orderGoodsDOList = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
            orderVO.setGoodsList(orderGoodsDOList);

            //handleOption
            OrderHandleOption handleOption = null;
            switch (orderStatus){
                case 101:
                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
                    break;
                case 102:
                    handleOption = new OrderHandleOption(false,false,false,true,true,false,false);
                    break;
                case 103:
                    handleOption = new OrderHandleOption(false,false,false,true,true,false,false);
                    break;
                case 201:
                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
                    break;
                case 202:
                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
                    break;
                case 203:
                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
                    break;
                case 301:
                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
                    break;
                case 401:
                    handleOption = new OrderHandleOption(false,true,false,true,false,true,false);
                    break;
                case 402:
                    handleOption = new OrderHandleOption(false,true,false,true,false,true,false);
                    break;
            }
            orderVO.setHandleOption(handleOption);

//            boolean cancel = false;
//            if (orderStatus == 102 || orderStatus == 103){
//                cancel = true;
//            }
//            orderHandleOption.setCancel(cancel);
//            //delete
//            orderHandleOption.setDelete(orderDO.getDeleted());
//            //pay
//            boolean pay = false;
//            if (orderDO.getPayId() != null){
//                pay = true;
//            }
//            orderHandleOption.setPay(pay);
//            //comment
//            boolean comment = false;
//            if (orderDO.getComments() != null && orderDO.getComments() != 0){
//                comment = true;
//            }
//            orderHandleOption.setComment(comment);

            //加入orderVOList
            orderVOList.add(orderVO);

        }

        Map<String, Object> data = new HashMap<>();
        data.put("data",orderVOList);
        data.put("count",count);
        data.put("totalPages",(int)(Math.ceil(1.0*count/size)));


        return new BaseReqVo(data,"成功",0);
    }
}
