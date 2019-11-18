package com.wangdao.mall.service.wx;

import java.util.HashMap;

public interface WxGoodsService {
    HashMap<String, Object> queryGoodsCount();

    HashMap<String, Object> queryWxGoodsListBykeyword(String keyword, Integer page, Integer size, String sort, String order);
}
