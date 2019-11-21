/**
 * wx端商品收藏
 * User: Jql
 * Date  2019/11/20
 * Time  下午 3:13
 */

package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.CollectDO;
import com.wangdao.mall.service.wx.WxCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("wx")
public class WxCollectController {

    @Autowired
    WxCollectService wxCollectService;


    /**
     * 获取用户商品收藏列表
     * @param type
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("collect/list")
    public BaseReqVo collectList(Integer type,Integer page,Integer size){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxCollectService.queryCollectList(type,page,size);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    /**
     * 添加或取消收藏 (只做了商品，可能有添加专题收藏)
     * @param collectDO
     * @return
     */
    @RequestMapping("collect/addordelete")
    public BaseReqVo collectAddordelete(@RequestBody CollectDO collectDO){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        HashMap<String, Object> map = wxCollectService.collectAddordelete(collectDO);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
