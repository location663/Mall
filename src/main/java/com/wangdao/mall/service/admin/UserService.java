package com.wangdao.mall.service.admin;

import java.util.Map;

public interface UserService {
    Map listByUserCondition(int page, int limit,String sort,String order,String username,String mobile);
    Map listByAddressCondition(int page, int limit, String sort, String order, String name, String userid);
    Map listByCollectCondition(int page, int limit, String sort, String order, String valueId, String userId);
    Map listByFootprintCondition(int page, int limit, String sort, String order, String goodsId, String userId);
    Map listByHistoryCondition(int page, int limit, String sort, String order, String keyword, String userId);
    Map listByFeedbackCondition(int page, int limit, String sort, String order, String username, String id);
}
