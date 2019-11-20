package com.wangdao.mall.service.wx;

import java.util.HashMap;

public interface WxCatalogService {
    HashMap<String, Object> queryCatalogIndex();

    HashMap<String, Object> queryCatalogCurrent(Integer id);
}
