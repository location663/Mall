package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.GrouponDetailVO;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.service.wx.WxGrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxGrouponController {

    @Autowired
    WxGrouponService grouponService;

    /**
     * 团购列表
     * @param pageDTO
     * @return
     */
    @RequestMapping("groupon/list")
    public BaseReqVo grouponList(RequestPageDTO pageDTO){
        Map<String, Object> map = grouponService.listGroupon(pageDTO);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }

    /**
     * 团购详情
     * @param grouponId
     * @return
     */
    @RequestMapping("groupon/detail")
    public BaseReqVo grouponDetail(Integer grouponId){
        GrouponDetailVO grouponDetailVO = grouponService.selectById(grouponId);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(grouponDetailVO, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("groupon/my")
    public BaseReqVo myGroupon(Integer showType){
        Map map = grouponService.listByUseridAndShowtype(showType);
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }
}
