package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.GrouponRulesDO;
import com.wangdao.mall.bean.RequestPageDTO;

import java.util.Map;

public interface GrouponService {
    Map listGrouponRecord(RequestPageDTO pageDTO);

    Map listGrouponRules(RequestPageDTO pageDTO);

    GrouponRulesDO createGrouponRules(GrouponRulesDO grouponRulesDO);
}
