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

import java.util.HashMap;

@RestController
@RequestMapping("wx")
public class WxSearchController {

    @Autowired
    WxSearchService wxSearchService;


    /**
     * 搜索关键字  必须是"isHot"=true和"deleted"=false   "historyKeywordList": []需要待改进
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
}
