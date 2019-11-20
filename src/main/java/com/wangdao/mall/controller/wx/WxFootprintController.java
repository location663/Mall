package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.FootprintDO;
import com.wangdao.mall.service.util.wx.BaseRespVo;
import com.wangdao.mall.service.wx.WxFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-19 22:39
 **/
@RestController
@RequestMapping("wx")
public class WxFootprintController {
    @Autowired
    WxFootprintService footprintService;

    @RequestMapping("footprint/list")
    public BaseRespVo listFootprint(Integer page, Integer size){
        Map<String, Object> map = footprintService.listFootprint(page, size);
//        BaseRespVo baseRespVo = new BaseRespVo();
//        baseRespVo.setData(map);
//        baseRespVo.setErrmsg("成功");
//        baseRespVo.setErrno(0);
//        return baseRespVo;
        return BaseRespVo.ok(map);
    }

    @RequestMapping("footprint/delete")
    public BaseRespVo deleteFootprint(@RequestBody Map<String, Integer> map){
        int delete = footprintService.deleteFootprint(map.get("id"));
        if(delete == 1) {
            return BaseRespVo.ok(null);
        }
        return BaseRespVo.fail();
    }
}
