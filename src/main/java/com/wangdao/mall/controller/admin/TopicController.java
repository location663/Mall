package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.TopicDO;
import com.wangdao.mall.service.admin.TopicDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-18 17:15
 **/
@RestController
@RequestMapping("admin")
public class TopicController {
    @Autowired
    TopicDOService topicDOService;

    @RequestMapping("topic/list")
    public BaseReqVo queryTopicList(Integer page, Integer limit, String title, String subtitle){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = topicDOService.queryTopocList(page, limit, title, subtitle);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("topic/create")
    public BaseReqVo createTopic(@RequestBody TopicDO topicDO){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        TopicDO topicDO1 = topicDOService.creatTopic(topicDO);
        baseReqVo.setData(topicDO1);
        baseReqVo.setErrmsg("");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


}
