package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.TopicDO;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-18 17:18
 **/
public interface TopicDOService {

    Map<String, Object> queryTopocList(Integer page, Integer limit, String title, String subtitle);

    TopicDO creatTopic(TopicDO topicDO);
}
