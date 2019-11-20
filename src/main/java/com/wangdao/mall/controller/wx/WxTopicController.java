/**
 * Created by Intellij IDEA
 * User:cookie
 * Date:2019/11/18
 * Time:20:58
 **/
package com.wangdao.mall.controller.wx;
import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.TopicListDoBean;
import com.wangdao.mall.service.wx.WxTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("wx")
public class WxTopicController {

    @Autowired
    WxTopicService topicService;

    @RequestMapping("topic/detail")
    public BaseReqVo topicDetail(String id){
        Map map=topicService.topicDetail(id);
        return new BaseReqVo(map,"成功",0);
    }

    @RequestMapping("comment/list")
    public BaseReqVo commentList(String valueId,String type,String showType,String page,String size){
        TopicListDoBean topicListDoBean = topicService.commentList(valueId, type, showType, page, size);
        return new BaseReqVo(topicListDoBean,"成功",0);
    }

    @RequestMapping("topic/list")
    public BaseReqVo topicList(String page,String size){
        Map map=topicService.topicList(page,size);
        return new BaseReqVo(map,"成功",0);
    }

    @RequestMapping("topic/related")
    public BaseReqVo topicRelated(Integer id){
        Set set=topicService.topicRelated(id);
        return new BaseReqVo(set,"成功",0);
    }
}
