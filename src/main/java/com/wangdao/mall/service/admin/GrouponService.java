package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.GrouponRulesDO;
import com.wangdao.mall.bean.RequestPageDTO;

import java.util.Map;

public interface GrouponService {

    Map listGrouponRules(RequestPageDTO pageDTO);

    GrouponRulesDO createGrouponRules(GrouponRulesDO grouponRulesDO) throws Exception;

    int updateGrouponRules(GrouponRulesDO grouponRulesDO) throws Exception;

    int deleteGrouponRules(GrouponRulesDO grouponRulesDO);



    Map<String, Object> queryGrouponList(Integer page, Integer limit, Integer goodsId);
}
