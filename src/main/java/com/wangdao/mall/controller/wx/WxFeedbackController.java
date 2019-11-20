package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.FeedbackDO;
import com.wangdao.mall.service.util.wx.BaseRespVo;
import com.wangdao.mall.service.wx.WxFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 11:45
 **/
@RestController
@RequestMapping("wx")
public class WxFeedbackController {
    @Autowired
    WxFeedbackService wxfeedbackService;

    @RequestMapping("feedback/submit")
    public BaseRespVo submitFeedback(@RequestBody FeedbackDO feedbackDO){
        int insert = wxfeedbackService.submitFeedback(feedbackDO);
        if(insert == 1){
            return BaseRespVo.ok(null);
        }
        return BaseRespVo.createDataFailed();
    }
}
