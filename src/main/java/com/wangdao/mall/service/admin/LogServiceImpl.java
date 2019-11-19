/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 19:21
 **/
package com.wangdao.mall.service.admin;


import com.wangdao.mall.bean.LogDO;
import com.wangdao.mall.mapper.LogDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    LogDOMapper logDOMapper;

    @Override
    public void insertLog(LogDO logDO) {
        logDOMapper.insertSelective(logDO);
    }
}
