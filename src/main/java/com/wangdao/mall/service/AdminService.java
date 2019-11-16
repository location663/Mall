package com.wangdao.mall.service;

import com.wangdao.mall.bean.AdminDO;

import java.util.List;
import java.util.Map;

public interface AdminService {

     Map selectAllAdmin(String username,Integer page,Integer limit,String sort,String order);

     List<Map> selectAllAdminRole();

     List<AdminDO> createNewAdmin(AdminDO adminDO);

     AdminDO updateAdmin(AdminDO adminDO);
}
