/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:21:21
 **/
package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.TopicListDoBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WxTopicService {
    Map topicDetail(String id);
    TopicListDoBean commentList(String valueId, String type, String showType, String page, String size);
    Map topicList(String page, String size);
    Set topicRelated(Integer id);
}
