/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:21:22
 **/
package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.CommentDOMapper;
import com.wangdao.mall.mapper.TopicDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Service
public class WxTopicServiceImpl implements WxTopicService {
    @Autowired
    TopicDOMapper topicDOMapper;
    @Override
    public Map topicDetail(String id) {
        TopicDO topicDO = topicDOMapper.selectByPrimaryKey(Integer.parseInt(id));
        String[] goods = topicDO.getGoods();
        Map<String,Object> returnmap=new HashMap();
        returnmap.put("goods",goods);
        returnmap.put("topic",topicDO);
        return returnmap;
    }
    @Autowired
    CommentDOMapper commentDOMapper;
    @Autowired
    UserDOMapper userDOMapper;
    @Override
    public TopicListDoBean topicList(String valueID, String type, String showType, String page, String size) {
        TopicListDoBean topicListDoBean = new TopicListDoBean();
        List<TopicListDoBean.DataBean> dataBeanList=new ArrayList();
        CommentDOExample commentDOExample = new CommentDOExample();
        CommentDOExample.Criteria criteria = commentDOExample.createCriteria();
        criteria.andTypeEqualTo(Byte.parseByte(type)).andValueIdEqualTo(Integer.parseInt(valueID));
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(size));
        List<CommentDO> commentDOS = commentDOMapper.selectByExample(commentDOExample);
        for (CommentDO commentDO : commentDOS) {
            Integer userId = commentDO.getUserId();
            String[] picUrls = commentDO.getPicUrls();
            Date addTime = commentDO.getAddTime();
            String content = commentDO.getContent();
            UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
            TopicListDoBean.DataBean.UserInfoBean userInfoBean = new TopicListDoBean.DataBean.UserInfoBean(userDO.getNickname(), userDO.getAvatar());
            TopicListDoBean.DataBean dataBean = new TopicListDoBean.DataBean(userInfoBean,addTime.toString(),content,Arrays.asList(picUrls));
            dataBeanList.add(dataBean);
        }
        topicListDoBean.setData(dataBeanList);
        topicListDoBean.setCount(commentDOS.size());
        topicListDoBean.setCurrentPage(Integer.parseInt(page));
        return topicListDoBean;
    }
}
