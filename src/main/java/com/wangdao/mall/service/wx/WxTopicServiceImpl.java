/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:21:22
 **/
package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.TopicDO;
import com.wangdao.mall.bean.TopicDOExample;
import com.wangdao.mall.mapper.TopicDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxTopicServiceImpl implements WxTopicService {
    @Autowired
    TopicDOMapper topicDOMapper;
    @Override
    public Map topicDetail(String id) {
        TopicDO topicDO = topicDOMapper.selectByPrimaryKey(Integer.parseInt(id));
        String goods = topicDO.getGoods();
        Map<String,Object> returnmap=new HashMap();
        returnmap.put("goods",goods);
        returnmap.put("topic",topicDO);
        return returnmap;
    }

    @Override
    public Map topicList(String valueID, String type, String showType, String page, String size) {
        return null;
    }
}
