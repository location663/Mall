/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/20
 * Time:16:56
 **/
package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CommentDO;
import com.wangdao.mall.bean.TopicListDoBean;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.service.wx.WxCommentService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx/comment")
public class WxCommentController {
    @Autowired
    WxCommentService commentService;
    @RequestMapping("list")
    public BaseReqVo list(String valueId, String type, String showType, String page, String size){
        TopicListDoBean topicListDoBean =commentService.commentList(valueId, type, showType, page, size);
        return new BaseReqVo(topicListDoBean,"成功",0);
    }
    @RequestMapping("post")
    public BaseReqVo post(@RequestBody CommentDO commentDO){
        Subject subject = SecurityUtils.getSubject();
        UserDO userDO = (UserDO)subject.getPrincipal();
        CommentDO commentDO1=commentService.post(commentDO,userDO);
        return new BaseReqVo(commentDO1,"成功",0);
    }
   @RequestMapping("count")
    public BaseReqVo count(Integer valueId,Integer type){
        Map map=commentService.count(valueId,type);
        return new BaseReqVo<>(map,"成功",0);
   }
}
