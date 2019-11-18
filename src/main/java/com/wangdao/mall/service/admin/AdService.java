package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.AdDO;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-15 17:44
 **/
public interface AdService {
    Map<String, Object> queryAdDOs(Integer page, Integer limit, String name, String content);

    AdDO queryAdDO(Integer id);

    int updateAdDO(AdDO adDO);

    int createAdDO(AdDO adDO);

    int deleteAdDOById(Integer id);
}
