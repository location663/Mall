package com.wangdao.mall.controller.admin;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.GrouponRulesDO;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.service.admin.GrouponService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class GrouponController {

    @Autowired
    GrouponService grouponService;

    /**
     * 团购活动列表
     * @param
     * @return
     */
//    @RequestMapping("groupon/listRecord")
//    public BaseReqVo grouponListRecord(RequestPageDTO pageDTO){
//        Map map = grouponService.listGrouponRecord(pageDTO);
//        BaseReqVo<Map> baseReqVo = new BaseReqVo<>(map, "成功", 0);
//        return baseReqVo;
//    }
    @RequestMapping("groupon/listRecord")
    @RequiresPermissions(value = {"admin:groupon:list","admin:groupon:update","admin:groupon:delete",
            "admin:groupon:create","admin:groupon:read"},logical = Logical.OR)
    public BaseReqVo queryGrouponList(Integer page, Integer limit, Integer goodsId){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = grouponService.queryGrouponList(page, limit, goodsId);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 团购规则列表
     * @param pageDTO
     * @return
     */
    @RequestMapping("groupon/list")
    @RequiresPermissions(value = {"admin:groupon:read","admin:groupon:update","admin:groupon:delete"
            ,"admin:groupon:create","admin:groupon:list"}, logical = Logical.OR)
    public BaseReqVo grouponRulesList(RequestPageDTO pageDTO){
        Map map = grouponService.listGrouponRules(pageDTO);
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>(map, "成功", 0);
        return baseReqVo;
    }

    /**
     * 创建团购规则
     * @param grouponRulesDO
     * @return
     * @throws Exception
     */
    @RequestMapping("groupon/create")
    @RequiresPermissions("admin:groupon:create")
    public BaseReqVo grouponRulesCreate(@RequestBody GrouponRulesDO grouponRulesDO) throws Exception {
        if (grouponRulesDO.getExpireTime().before(new Date())){
            return new BaseReqVo(null, null, 701);
        }
        GrouponRulesDO grouponRulesDO1 = grouponService.createGrouponRules(grouponRulesDO);
        BaseReqVo<GrouponRulesDO> baseReqVo = new BaseReqVo<>(grouponRulesDO1, "成功", 0);
        return baseReqVo;
    }

    /**
     * 更新团购规则
     * @param grouponRulesDO
     * @return
     * @throws Exception
     */
    @RequestMapping("groupon/update")
    @RequiresPermissions("admin:groupon:update")
    public BaseReqVo grouponRulesUpdate(@RequestBody GrouponRulesDO grouponRulesDO) throws Exception {
        if (grouponRulesDO.getExpireTime().before(new Date()) || grouponRulesDO.getExpireTime().before(grouponRulesDO.getAddTime())){
            return new BaseReqVo(null, null, 701);
        }
        int res = grouponService.updateGrouponRules(grouponRulesDO);
        BaseReqVo<Object> baseReqVo;
        if (res == 1) {
            baseReqVo = new BaseReqVo<>(null, "成功", 0);
        } else {
            baseReqVo = new BaseReqVo<>(null, null, 505);
        }
        return baseReqVo;
    }

    /**
     * 删除团购规则
     * @param grouponRulesDO
     * @return
     */
    @RequestMapping("groupon/delete")
    @RequiresPermissions("admin:groupon:delete")
    public BaseReqVo grouponRulesDelete(@RequestBody GrouponRulesDO grouponRulesDO){
        int res = grouponService.deleteGrouponRules(grouponRulesDO);
        BaseReqVo<Object> baseReqVo;
        if (res == 1) {
            baseReqVo = new BaseReqVo<>(null, "成功", 0);
        } else {
            baseReqVo = new BaseReqVo<>(null, null, 502);
        }
        return baseReqVo;
    }
}
