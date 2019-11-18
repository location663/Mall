package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.GrouponRecordVO;
import com.wangdao.mall.bean.GrouponRulesDO;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.service.admin.GrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class GrouponController {

    @Autowired
    GrouponService grouponService;

    @RequestMapping("groupon/listRecord")
    public BaseReqVo grouponListRecord(RequestPageDTO pageDTO){
        Map map = grouponService.listGrouponRecord(pageDTO);
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("groupon/list")
    public BaseReqVo grouponRulesList(RequestPageDTO pageDTO){
        Map map = grouponService.listGrouponRules(pageDTO);
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }

    @RequestMapping("groupon/create")
    public BaseReqVo grouponRulesCreate(@RequestBody GrouponRulesDO grouponRulesDO){
        GrouponRulesDO grouponRulesDO1 = grouponService.createGrouponRules(grouponRulesDO);
        BaseReqVo<GrouponRulesDO> baseReqVo = new BaseReqVo<>(grouponRulesDO1, "成功", 0);
        return baseReqVo;
    }
}
