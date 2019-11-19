/**
 * wx端Catalog接口
 * User: Jql
 * Date  2019/11/19
 * Time  下午 10:29
 */

package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.service.wx.WxCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("wx")
public class WxCatalogController {

    @Autowired
    WxCatalogService wxCatalogService;


    @RequestMapping("catalog/index")
    public BaseReqVo catalogIndex(){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxCatalogService.queryCatalogIndex();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
