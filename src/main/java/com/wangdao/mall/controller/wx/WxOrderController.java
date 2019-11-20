//package com.wangdao.mall.controller.wx;
//
//import com.wangdao.mall.bean.BaseReqVo;
//import com.wangdao.mall.service.wx.WxOrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("wx")
//public class WxOrderController {
//
//    @Autowired
//    WxOrderService wxOrderService;
//
//    @RequestMapping("order/submit")
//    public BaseReqVo submitOrder(@RequestBody Map<String, Object> map){
//        BaseReqVo baseReqVo = wxOrderService.submitOrder(map);
//        return baseReqVo;
//    }
//}
