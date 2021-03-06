package com.wangdao.mall.service.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.OrderGoodsDO;
import com.wangdao.mall.exception.WxException;

import java.util.Map;

public interface WxOrderService {

    BaseReqVo submitOrder(Map<String, Object> map) throws JsonProcessingException, WxException;

    BaseReqVo getOrderList(Integer showType, Integer page, Integer size);

    BaseReqVo getOrderDetail(Integer orderId);

    BaseReqVo CancelOrder(Map<String, Object> map);

    BaseReqVo orderPrepay(Map<String, Object> map) throws WxException;

    BaseReqVo orderRefund(Map<String, Object> map);

    BaseReqVo confirmOrder(Map<String, Object> map);

    BaseReqVo deleteOrder(Map<String, Object> map);

    OrderGoodsDO goodsOrder(Integer orderId, Integer goodsId);

    int insertComment(CommentDO commentDO);
}
