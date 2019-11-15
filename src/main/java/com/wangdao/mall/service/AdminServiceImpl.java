/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 17:11
 **/
package com.wangdao.mall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.AdminDO;
import com.wangdao.mall.bean.AdminDOExample;
import com.wangdao.mall.bean.RequestPageDTO;
import com.wangdao.mall.mapper.AdminDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminDOMapper adminDOMapper;

    @Override
    public Map selectAllAdmin(String username,Integer page,Integer limit,String sort,String order) {

        AdminDOExample adminDOExample = new AdminDOExample();
        PageHelper.startPage(page,limit);

        if (username != null){
            adminDOExample.createCriteria().andUsernameLike("%" + username + "%");
        }
        adminDOExample.createCriteria();
        adminDOExample.setOrderByClause(sort + " " + order);
        List<AdminDO> adminDOList = adminDOMapper.selectByExample(adminDOExample);

        PageInfo<AdminDO> userPageInfo = new PageInfo<>(adminDOList);
        long total = userPageInfo.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", adminDOList);

        return map;

    }

}
