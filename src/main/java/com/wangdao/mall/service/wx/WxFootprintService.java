package com.wangdao.mall.service.wx;

import com.wangdao.mall.exception.WxException;

import java.util.Map;

/**
 * <h3>Mall</h3>
 * <p></p>
 *
 * @author : uyn4j
 * @date : 2019-11-19 22:45
 **/
public interface WxFootprintService {

    Map<String, Object> listFootprint(Integer page, Integer size) throws WxException;

    int deleteFootprint(Integer id);
}
