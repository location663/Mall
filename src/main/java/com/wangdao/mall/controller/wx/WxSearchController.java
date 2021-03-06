/**
 * WX端搜索模块
 * User: Jql
 * Date  2019/11/19
 * Time  下午 11:25
 */

package com.wangdao.mall.controller.wx;


import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.wx.WxSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("wx")
public class WxSearchController {

    @Autowired
    WxSearchService wxSearchService;


    /**
     * 搜索关键字  必须是"isHot"=true和"deleted"=false   "historyKeywordList": []如果用户未登录，直接返回空List
     * @return
     */
    @RequestMapping("search/index")
    public BaseReqVo searchIndex(){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxSearchService.searchIndex();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     * 搜索帮助
     * @param keyword
     * @return
     */
    @RequestMapping("search/helper")
    public BaseReqVo searchHelper(String keyword){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        ArrayList<String> keywords = wxSearchService.searchHelper(keyword);
        baseReqVo.setData(keywords);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     * 清除搜索历史记录
     * @return
     */
    @RequestMapping("search/clearhistory")
    public BaseReqVo searchClearhistory(){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Integer i = wxSearchService.searchClearhistory();

        baseReqVo.setData(null);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
