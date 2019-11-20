package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.FeedbackDO;
import org.springframework.stereotype.Service;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 11:50
 **/
public interface WxFeedbackService {

    int submitFeedback(FeedbackDO feedbackDO);

}
