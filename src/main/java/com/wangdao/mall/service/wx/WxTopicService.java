/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:21:21
 **/
package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.TopicListDoBean;

import java.util.Map;

public interface WxTopicService {
    Map topicDetail(String id);
    TopicListDoBean topicList(String valueID, String type, String showType, String page, String size);
}
