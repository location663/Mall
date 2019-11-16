package com.wangdao.mall.service.admin;

import java.util.Map;

public interface UserService {
    Map listByUserCondition(int page, int limit,String sort,String order,String username,String mobile);
    Map listByAddressCondition(int page, int limit, String sort, String order, String name, Integer userid);
    Map listByCollectCondition(int page, int limit, String sort, String order, Integer valueId, Integer userId);
    Map listByFootprintCondition(int page, int limit, String sort, String order, Integer goodsId, Integer userId);
    Map listByHistoryCondition(int page, int limit, String sort, String order, String keyword, Integer userId);
    Map listByFeedbackCondition(int page, int limit, String sort, String order, String username, Integer id);
}
