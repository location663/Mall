package com.wangdao.mall.controller.wx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.OrderGoodsDO;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.service.wx.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxOrderController {

    @Autowired
    WxOrderService wxOrderService;

    @RequestMapping("order/submit")
    public BaseReqVo submitOrder(@RequestBody Map<String, Object> map) throws JsonProcessingException, WxException {
        BaseReqVo baseReqVo = wxOrderService.submitOrder(map);
        return baseReqVo;
    }

    @RequestMapping("order/list")
    public BaseReqVo getOrderList(Integer showType, Integer page, Integer size){
        BaseReqVo baseReqVo = wxOrderService.getOrderList(showType,page,size);
        return baseReqVo;
    }

    @RequestMapping("order/detail")
    public BaseReqVo getOrderDetail(Integer orderId){
        BaseReqVo baseReqVo = wxOrderService.getOrderDetail(orderId);
        return baseReqVo;
    }

    @RequestMapping("order/cancel")
    public BaseReqVo CancelOrder(@RequestBody Map<String, Object> map){
        BaseReqVo baseReqVo = wxOrderService.CancelOrder(map);
        return baseReqVo;
    }

    @RequestMapping("order/prepay")
    public BaseReqVo orderPrepay(@RequestBody Map<String, Object> map) throws WxException {
        BaseReqVo baseReqVo = wxOrderService.orderPrepay(map);
//        if (baseReqVo.getErrno() == 0){
//            throw new WxException("支付成功");
//        }
        return baseReqVo;
    }

    @RequestMapping("order/refund")
    public BaseReqVo orderRefund(@RequestBody Map<String, Object> map){
        BaseReqVo baseReqVo = wxOrderService.orderRefund(map);
        return baseReqVo;
    }

    @RequestMapping("order/confirm")
    public BaseReqVo confirmOrder(@RequestBody Map<String, Object> map){
        BaseReqVo baseReqVo = wxOrderService.confirmOrder(map);
        return baseReqVo;
    }

    @RequestMapping("order/delete")
    public BaseReqVo deleteOrder(@RequestBody Map<String, Object> map){
        BaseReqVo baseReqVo = wxOrderService.deleteOrder(map);
        return baseReqVo;
    }

    @RequestMapping("order/goods")
    public BaseReqVo goodsOrder(Integer orderId, Integer goodsId){
        OrderGoodsDO orderGoodsDO = wxOrderService.goodsOrder(orderId, goodsId);
        return new BaseReqVo(orderGoodsDO, "成功", 0);
    }

    @RequestMapping("order/comment")
    public BaseReqVo orderComment(@RequestBody CommentDO commentDO){
        int res = wxOrderService.insertComment(commentDO);
        return new BaseReqVo(res, "成功", 0);
    }

}
