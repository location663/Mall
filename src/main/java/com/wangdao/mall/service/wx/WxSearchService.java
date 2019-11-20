package com.wangdao.mall.service.wx;

import java.util.ArrayList;
import java.util.HashMap;

public interface WxSearchService {
    HashMap<String, Object> searchIndex();

    ArrayList<String> searchHelper(String keyword);

}
