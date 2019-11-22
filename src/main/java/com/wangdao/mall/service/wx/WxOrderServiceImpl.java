package com.wangdao.mall.service.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.mapper.*;
import com.wangdao.mall.service.util.GetOrderHandleOption;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class WxOrderServiceImpl implements WxOrderService {

    @Autowired
    CouponUserDOMapper couponUserDOMapper;

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
    public BaseReqVo submitOrder(Map<String, Object> map) throws JsonProcessingException, WxException {

        OrderDO orderDO = new OrderDO();
        UserDO userDO  = (UserDO) SecurityUtils.getSubject().getPrincipal();

        //订单状态
        orderDO.setOrderStatus((short) 101);

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
        if (orderDO.getGoodsPrice().doubleValue() > Double.valueOf(keyValue)){
            orderDO.setFreightPrice(BigDecimal.valueOf(0));
        }else {
            SystemDO systemDOValue = systemDOMapper.selectByPrimaryKey(7);
            orderDO.setFreightPrice(BigDecimal.valueOf(Double.valueOf(systemDOValue.getKeyValue())));
        }

        //待评价订单商品数量
        orderDO.setComments((short)cartDOList.size());
//        CommentDOExample commentDOExample = new CommentDOExample();
////        commentDOExample.createCriteria().andValueIdEqualTo(cartDO.getGoodsId()).andDeletedEqualTo(false);
////        List<CommentDO> commentDOList = commentDOMapper.selectByExample(commentDOExample);
////        if (commentDOList == null){
//            orderDO.setComments((short) 1);
//        }else {
//            orderDO.setComments((short) 0);
//        }

        //优惠券信息
        Integer couponUserId = (Integer) map.get("couponId");
        CouponUserDO couponUserDO1 = couponUserDOMapper.selectByPrimaryKey(couponUserId);
        Integer couponId = couponUserDO1.getCouponId();
        CouponDO couponDO = couponDOMapper.selectByPrimaryKey(couponId);
        if (couponDO != null) {
            if (orderDO.getGoodsPrice().doubleValue() - couponDO.getMin().doubleValue() >= 0) {
                //优惠券优惠价格
                orderDO.setCouponPrice(couponDO.getDiscount());
            }else {
                orderDO.setCouponPrice(BigDecimal.valueOf(0));
            }
        }else {
            orderDO.setCouponPrice(BigDecimal.valueOf(0));
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
                throw new WxException("团购活动已过期");
//                return new BaseReqVo(null, "团购活动已过期", 730);
            }
        }else {
            orderDO.setGrouponPrice(BigDecimal.valueOf(0));
        }

        //订单付款时留言
        String message = (String) map.get("message");
        orderDO.setMessage(message);

        //订单费用
        double orderPrice = orderDO.getGoodsPrice().doubleValue()+orderDO.getFreightPrice().doubleValue()/*-orderDO.getCouponPrice().doubleValue()*/;
        if (orderDO.getCouponPrice() != null){
            orderPrice = orderPrice - orderDO.getCouponPrice().doubleValue();
        }
        orderDO.setOrderPrice(BigDecimal.valueOf(orderPrice));

        //实付费用
        double actualPrice = orderDO.getOrderPrice().doubleValue()- orderDO.getIntegralPrice().doubleValue();
        orderDO.setActualPrice(BigDecimal.valueOf(actualPrice));

//        //微信付款时间
//        orderDO.setPayTime(new Date());


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

        //暂时初始化值订单编号、微信付款编号和发货编号
        orderDO.setOrderSn("0");
        orderDO.setPayId("0");
        orderDO.setShipSn("0");

        //插入order数据
        orderDOMapper.insertSelective(orderDO);

        //更改订单编号、微信付款编号和发货编号
        int i = orderDOMapper.selectLastInsertId();
        orderDO.setOrderSn(String.valueOf(i));
//        orderDO.setPayId(String.valueOf(i));
        orderDO.setId(i);
        orderDOMapper.updateByPrimaryKeySelective(orderDO);

        //修改优惠券信息
        if (orderDO.getCouponPrice() != BigDecimal.valueOf(0)) {
            couponUserDO1.setId(couponUserId);
            couponUserDO1.setStatus((short)1);
            couponUserDO1.setUsedTime(new Date());
            couponUserDO1.setOrderId(i);
            couponUserDO1.setUpdateTime(new Date());

//            Integer id = userDO.getId();
//            CouponUserDOExample couponUserDOExample = new CouponUserDOExample();
//            couponUserDOExample.createCriteria().andUserIdEqualTo(id).andCouponIdEqualTo(couponId).andDeletedEqualTo(false);
//            CouponUserDO couponUserDO = new CouponUserDO();
//            couponUserDO.setStatus((short) 1);
//            couponUserDO.setUsedTime(new Date());
//            couponUserDO.setOrderId(i);
//            couponUserDO.setUpdateTime(new Date());
            couponUserDOMapper.updateByPrimaryKeySelective(couponUserDO1);
//            couponUserDOMapper.updateByExampleSelective(couponUserDO1, couponUserDOExample);
        }

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

            //order_goods表商品货品的id
            orderGoodsDO.setProductId(cartDO.getProductId());

            //order_goods表商品货品的购买数量
            orderGoodsDO.setNumber(cartDO.getNumber());

            //order_goods表商品货品的售价
            orderGoodsDO.setPrice(cartDO.getPrice());

            //order_goods表商品货品的规格列表
            ObjectMapper objectMapper = new ObjectMapper();
            String[] strings = objectMapper.readValue(cartDO.getSpecifications(), String[].class);
            orderGoodsDO.setSpecifications(strings);

            //order_goods表商品货品的图片
            orderGoodsDO.setPicUrl(cartDO.getPicUrl());

            //生成订单后减少库存中商品的数量
            Integer productId = cartDO.getProductId();
            Integer number = goodsProductDOMapper.selectByPrimaryKey(productId).getNumber();
            number = number - cartDO.getNumber();
            GoodsProductDO goodsProductDO = new GoodsProductDO();
            goodsProductDO.setId(productId);
            goodsProductDO.setNumber(number);
            goodsProductDOMapper.updateByPrimaryKeySelective(goodsProductDO);

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
        if (cartId == 0) {
            CartDO cartDO = new CartDO();
            cartDO.setDeleted(true);
            CartDOExample cartDOExample = new CartDOExample();
            cartDOExample.createCriteria().andDeletedEqualTo(false).andCheckedEqualTo(true);
            cartDOMapper.updateByExampleSelective(cartDO, cartDOExample);
        }else {
            CartDO cartDO = new CartDO();
            cartDO.setId(cartId);
            cartDO.setDeleted(true);
            cartDOMapper.updateByPrimaryKeySelective(cartDO);
        }

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

        //更改所有未付款订单中所有过期的订单状态
        OrderDOExample orderStatuDOExample = new OrderDOExample();
        orderStatuDOExample.createCriteria().andOrderStatusEqualTo((short)101).andDeletedEqualTo(false).andUserIdEqualTo(user.getId());
        List<OrderDO> orderStatuDOList = orderDOMapper.selectByExample(orderStatuDOExample);
        for (OrderDO orderDO : orderStatuDOList) {
            if (new Date().after(orderDO.getEndTime())){
                orderDO.setOrderStatus((short)103);
                orderDO.setUpdateTime(new Date());
                orderDOMapper.updateByPrimaryKeySelective(orderDO);
            }
        }

        //转换状态码
        List<Short> statuList = new ArrayList();
//        int statuInt = 0;
        switch (showType){
            case 1:
                statuList.add((short)101);
                //订单取消后不显示在未付款栏中
//                statuList.add((short)102);
//                statuList.add((short)103);
//                statuInt = 101;
                break;
            case 2:
                statuList.add((short)201);
                statuList.add((short)202);
                //订单成功退款后不显示在待发货栏中
//                statuList.add((short)203);
//                statuInt = 201;
                break;
            case 3:
                statuList.add((short)301);
//                statuInt = 301;
                break;
            case 4:
                statuList.add((short)401);
                statuList.add((short)402);
//                statuInt = 401;
                break;
        }

        //获取符合userId和订单状态条件的order
        List<OrderDO> orderDOList = new ArrayList<>();
        OrderDOExample orderDOExample = new OrderDOExample();
        if (showType != 0) {
            PageHelper.startPage(page,size);
            orderDOExample.createCriteria().andUserIdEqualTo(user.getId()).andOrderStatusIn(statuList)/*.andOrderStatusEqualTo((short) statuInt)*/.andDeletedEqualTo(false);
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
                case 102:
                    orderStatusText = "已取消";
                    break;
                case 103:
                    orderStatusText = "已取消";
                    break;
                case 201:
                    orderStatusText = "已付款";
                    break;
                case 202:
                    orderStatusText = "申请退款";
                    break;
                case 203:
                    orderStatusText = "已退款";
                    break;
                case 301:
                    orderStatusText = "已发货";
                    break;
                case 401:
                    orderStatusText = "已收货";
                    break;
                case 402:
                    orderStatusText = "已收货";
                    break;
                case 0:
                    orderStatusText = "已评价";
                    break;
            }
            orderVO.setOrderStatusText(orderStatusText);

            //isGroupin
            BigDecimal grouponPrice = orderDOMapper.selectByPrimaryKey(orderDO.getId()).getGrouponPrice();
            if (grouponPrice.doubleValue() != 0){
                orderVO.setIsGroupin(true);
            }else {
                orderVO.setIsGroupin(false);
            }

            //orderSn
            orderVO.setOrderSn(orderDO.getOrderSn());

            //actualPrice
            orderVO.setActualPrice(orderDO.getActualPrice().doubleValue());

            //goodsList
            OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
            orderGoodsDOExample.createCriteria().andOrderIdEqualTo(orderDO.getId()).andDeletedEqualTo(false);
            List<OrderGoodsDO> orderGoodsDOList = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
//            OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
//            orderGoodsDOExample.createCriteria().andGoodsIdEqualTo(orderDO).andDeletedEqualTo(false);
//            List<OrderGoodsDO> orderGoodsDOList = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
            orderVO.setGoodsList(orderGoodsDOList);

            //handleOption
//            OrderHandleOption handleOption = null;
//            switch (orderStatus){
//                case 101:
//                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
//                    break;
//                case 102:
//                    handleOption = new OrderHandleOption(false,false,false,true,true,false,false);
//                    break;
//                case 103:
//                    handleOption = new OrderHandleOption(false,false,false,true,true,false,false);
//                    break;
//                case 201:
//                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
//                    break;
//                case 202:
//                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
//                    break;
//                case 203:
//                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
//                    break;
//                case 301:
//                    handleOption = new OrderHandleOption(true,false,false,false,true,false,false);
//                    break;
//                case 401:
//                    handleOption = new OrderHandleOption(false,true,false,true,false,true,false);
//                    break;
//                case 402:
//                    handleOption = new OrderHandleOption(false,true,false,true,false,true,false);
//                    break;
//            }
            orderVO.setHandleOption(GetOrderHandleOption.getOrderHandleOption(orderStatus));

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

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    @Override
    public BaseReqVo getOrderDetail(Integer orderId) {

        Map<String, Object> map = new HashMap<>();

        //获取订单详情
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);

        //获取orderInfo
        OrderInfoVO orderInfoVO = new OrderInfoVO();

        //consignee
        orderInfoVO.setConsignee(orderDO.getConsignee());

        //address
        orderInfoVO.setAddress(orderDO.getAddress());

        //addTime
        orderInfoVO.setAddTime(orderDO.getAddTime());

        //orderSn
        orderInfoVO.setOrderSn(orderDO.getOrderSn());

        //actualPrice
        orderInfoVO.setActualPrice(orderDO.getActualPrice().doubleValue());

        //mobile
        orderInfoVO.setMobile(orderDO.getMobile());

        //orderStatusText
        String statuText = null;
        switch (orderDO.getOrderStatus()){
            case 101:
                statuText = "未付款";
                break;
            case 102:
                statuText = "已取消";
                break;
            case 103:
                statuText = "已取消";
                break;
            case 201:
                statuText = "已付款";
                break;
            case 202:
                statuText = "申请退款";
                break;
            case 203:
                statuText = "已退款";
                break;
            case 301:
                statuText = "已发货";
                break;
            case 401:
                statuText = "已收货";
                break;
            case 402:
                statuText = "已收货";
                break;
            case 0:
                statuText = "已评价";
                break;
        }
        orderInfoVO.setOrderStatusText(statuText);

        //goodsPrice
        orderInfoVO.setGoodsPrice(orderDO.getGoodsPrice().doubleValue());

        //couponPrice
        orderInfoVO.setCouponPrice(orderDO.getCouponPrice().doubleValue());

        //id
        orderInfoVO.setId(orderDO.getId());

        //freightPrice
        orderInfoVO.setFreightPrice(orderDO.getFreightPrice().doubleValue());

        //handleOption
        orderInfoVO.setHandleOption(GetOrderHandleOption.getOrderHandleOption((int)orderDO.getOrderStatus()));

        //orderGoods
        OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
        orderGoodsDOExample.createCriteria().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        List<OrderGoodsDO> orderGoodsDOList = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);

        //物流信息
        Map<String, Object> expressInfo = new HashMap<>();
        expressInfo.put("logisticCode",orderDO.getShipSn());
        expressInfo.put("shipperName",orderDO.getShipChannel());

        map.put("orderInfo",orderInfoVO);
        map.put("orderGoods",orderGoodsDOList);
        map.put("expressInfo",expressInfo);

        return new BaseReqVo(map,"成功",0);
    }

    /**
     * 取消订单
     * @param map
     * @return
     */
    @Override
    public BaseReqVo CancelOrder(Map<String, Object> map) {
        Integer orderId = (Integer) map.get("orderId");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setOrderStatus((short)102);
        orderDO.setUpdateTime(new Date());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);
        OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
        orderGoodsDOExample.createCriteria().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
        List<OrderGoodsDO> orderGoodsDOList = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
        for (OrderGoodsDO orderGoodsDO : orderGoodsDOList) {
            Integer productId = orderGoodsDO.getProductId();
            Integer number = goodsProductDOMapper.selectByPrimaryKey(productId).getNumber() + orderGoodsDO.getNumber();
            GoodsProductDO goodsProductDO = new GoodsProductDO();
            goodsProductDO.setId(productId);
            goodsProductDO.setNumber(number);
            goodsProductDOMapper.updateByPrimaryKeySelective(goodsProductDO);
        }
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 订单的预支付会话
     * @param map
     * @return
     */
    @Override
    public BaseReqVo orderPrepay(Map<String, Object> map) throws WxException {
        if (map.get("orderId") instanceof Integer){
            return new BaseReqVo(null,"订单不能支付",724);
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("timeStamp","100");
        map1.put("nonceStr","100");
        map1.put("packageValue","100");
        map1.put("signType","100");
        map1.put("paySign","100");
        Integer orderId = Integer.valueOf((String) map.get("orderId"));
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        if (new Date().after(orderDO.getEndTime())) {
            OrderDO orderDOTime = new OrderDO();
            orderDOTime.setId(orderId);
            orderDOTime.setOrderStatus((short)103);
            orderDOTime.setUpdateTime(new Date());
            orderDOMapper.updateByPrimaryKeySelective(orderDOTime);
            throw new WxException("订单时间已过");
        }
        OrderDO orderDOSucceed = new OrderDO();
        orderDOSucceed.setId(orderId);
        orderDOSucceed.setOrderStatus((short)201);
        orderDOSucceed.setUpdateTime(new Date());
        orderDOSucceed.setPayId(String.valueOf(orderId));
        orderDOSucceed.setPayTime(new Date());
        orderDOMapper.updateByPrimaryKeySelective(orderDOSucceed);
        return new BaseReqVo(map1,"成功",0);
    }

    /**
     * 退款取消订单
     * @param map
     * @return
     */
    @Override
    public BaseReqVo orderRefund(Map<String, Object> map) {
        Integer orderId = (Integer) map.get("orderId");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setOrderStatus((short)202);
        orderDO.setUpdateTime(new Date());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 确认收货
     * @param map
     * @return
     */
    @Override
    public BaseReqVo confirmOrder(Map<String, Object> map) {
        Integer orderId = (Integer) map.get("orderId");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setOrderStatus((short)401);
        orderDO.setConfirmTime(new Date());
        orderDO.setUpdateTime(new Date());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 删除订单
     * @param map
     * @return
     */
    @Override
    public BaseReqVo deleteOrder(Map<String, Object> map) {
        Integer orderId = (Integer) map.get("orderId");
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setDeleted(true);
        orderDO.setUpdateTime(new Date());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);
        return new BaseReqVo(null,"成功",0);
    }

    /**
     * 评价页订单
     * @param orderId
     * @param goodsId
     * @return
     */
    @Override
    public OrderGoodsDO goodsOrder(Integer orderId, Integer goodsId) {
        OrderGoodsDOExample orderGoodsDOExample = new OrderGoodsDOExample();
        orderGoodsDOExample.createCriteria().andDeletedEqualTo(false)
                .andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId);
        List<OrderGoodsDO> orderGoodsDOS = orderGoodsDOMapper.selectByExample(orderGoodsDOExample);
        OrderGoodsDO orderGoodsDO = orderGoodsDOS.get(0);
        orderGoodsDO.setGoodsSpecificationValues(orderGoodsDO.getSpecifications());
        return orderGoodsDO;
    }

    /**
     * 插入评价
     * @param commentDO
     * @return
     */
    @Override
    public int insertComment(CommentDO commentDO) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        commentDO.setType((byte) 0);
        OrderGoodsDO orderGoodsDO = orderGoodsDOMapper.selectByPrimaryKey(commentDO.getOrderGoodsId());
        commentDO.setValueId(orderGoodsDO.getGoodsId());
        commentDO.setUserId(userDO.getId());
        if (commentDO.getPicUrls().length == 0){
            commentDO.setHasPicture(false);
        } else {
            commentDO.setHasPicture(true);
        }
        commentDO.setAddTime(new Date());
        commentDO.setUpdateTime(new Date());
        int res = commentDOMapper.insertSelective(commentDO);
        orderDOMapper.updateStatusAndCommentsByOrderId(commentDO.getOrderGoodsId(), 0);
        return res;
    }
}
