package com.wangdao.mall.service.wx;

import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.TopicListDoBean;
import com.wangdao.mall.bean.UserDO;
import java.util.Map;

public interface WxCommentService {
    TopicListDoBean commentList(Integer valueId, Integer type, Integer showType, Integer page, Integer size);
    CommentDO post(CommentDO commentDO, UserDO userDO);
    Map count(Integer valueId, Integer type);
}
