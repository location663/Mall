package com.wangdao.mall.service.wx;

import java.util.HashMap;

public interface WxSearchService {
    HashMap<String, Object> searchIndex();

    HashMap<String, Object> searchHelper(String keyword);

}
