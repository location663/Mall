package com.wangdao.mall.service.wx;
import java.util.Date;

import com.wangdao.mall.bean.FeedbackDO;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.mapper.FeedbackDOMapper;
import com.wangdao.mall.mapper.FootprintDOMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-20 11:50
 **/
@Service
public class WxFeedbackServiceImpl implements WxFeedbackService{
    @Autowired
    FeedbackDOMapper feedbackDOMapper;


    /**新增反馈,修改了自动生成的xml里的insert sql语句
     * @param feedbackDO
     * @return
     */
    @Override
    public int submitFeedback(FeedbackDO feedbackDO) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        feedbackDO.setUserId(userDO.getId());
        feedbackDO.setUsername(userDO.getUsername());
        feedbackDO.setStatus(0);
        feedbackDO.setAddTime(new Date());
        feedbackDO.setUpdateTime(new Date());
        feedbackDO.setDeleted(false);
        int insert = feedbackDOMapper.insert(feedbackDO);
        return insert;
    }
}
