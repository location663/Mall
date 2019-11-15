package com.wangdao.mall.service.admin;
import com.wangdao.mall.bean.GoodsDO;

import java.util.HashMap;
import java.util.List;

public interface GoodsService {


    HashMap<String, Object> queryGoodsList(Integer page, Integer limit, Integer goodsSn,String name,String sort, String order);

    HashMap<String, Object> queryGoodsDetail(Integer id);

    HashMap<String, Object> queryGoodsCatAndBrandList();

}
