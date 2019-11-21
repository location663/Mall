package com.wangdao.mall.service.wx;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-19 22:45
 **/
public interface WxFootprintService {

    Map<String, Object> listFootprint(Integer page, Integer size);

    int deleteFootprint(Integer id);
}
