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

import java.util.Map;

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
    @RequestMapping("topic/list")
    public BaseReqVo topicList(String valueID,String type,String showType,String page,String size){
        TopicListDoBean topicListDoBean = topicService.topicList(valueID, type, showType, page, size);
        return new BaseReqVo(topicListDoBean,"成功",0);
    }
}
