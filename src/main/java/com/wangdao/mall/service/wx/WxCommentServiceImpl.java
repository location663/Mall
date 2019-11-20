/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/20
 * Time:16:58
 **/
package com.wangdao.mall.service.wx;

import com.github.pagehelper.PageHelper;
import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.CommentDOExample;
import com.wangdao.mall.bean.TopicListDoBean;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.mapper.CommentDOMapper;
import com.wangdao.mall.mapper.UserDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class WxCommentServiceImpl implements WxCommentService {
    /**
     * 展示专题下的评论
     */
    @Autowired
    CommentDOMapper commentDOMapper;
    @Autowired
    UserDOMapper userDOMapper;
    @Override
    public TopicListDoBean commentList(String valueId, String type, String showType, String page, String size) {
        TopicListDoBean topicListDoBean = new TopicListDoBean();
        List<TopicListDoBean.DataBean> dataBeanList=new ArrayList();
        CommentDOExample commentDOExample = new CommentDOExample();
        CommentDOExample.Criteria criteria = commentDOExample.createCriteria();
        criteria.andTypeEqualTo(Byte.parseByte(type)).andValueIdEqualTo(Integer.parseInt(valueId));
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(size));
        List<CommentDO> commentDOS = commentDOMapper.selectByExample(commentDOExample);
        for (CommentDO commentDO : commentDOS) {
            Integer userId = commentDO.getUserId();
            String[] picUrls = commentDO.getPicUrls();
            Date addTime = commentDO.getAddTime();
            String content = commentDO.getContent();
            UserDO userDO = userDOMapper.selectByPrimaryKey(userId);
            TopicListDoBean.DataBean.UserInfoBean userInfoBean = new TopicListDoBean.DataBean.UserInfoBean(userDO.getNickname(), userDO.getAvatar());
            TopicListDoBean.DataBean dataBean = new TopicListDoBean.DataBean(userInfoBean,addTime.toString(),content, Arrays.asList(picUrls));
            dataBeanList.add(dataBean);
        }
        topicListDoBean.setData(dataBeanList);
        topicListDoBean.setCount(commentDOS.size());
        topicListDoBean.setCurrentPage(Integer.parseInt(page));
        return topicListDoBean;
    }
    @Override
    public CommentDO post(CommentDO commentDO,UserDO userDO) {
        commentDO.setAddTime(new Date());
        commentDO.setUpdateTime(new Date());
        commentDO.setUserId(userDO.getId());
        commentDOMapper.insertSelective(commentDO);
        CommentDO commentDO1 = commentDOMapper.selectByPrimaryKey(commentDOMapper.selectLastInsert());
        return commentDO1;
    }
}
