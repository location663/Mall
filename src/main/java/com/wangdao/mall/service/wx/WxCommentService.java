package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.TopicListDoBean;
import com.wangdao.mall.bean.UserDO;

public interface WxCommentService {
    TopicListDoBean commentList(String valueId, String type, String showType, String page, String size);
    CommentDO post(CommentDO commentDO, UserDO userDO);
}
