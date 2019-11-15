package com.wangdao.mall.service;

import com.wangdao.mall.bean.RequestPageDTO;

import java.util.Map;

public interface AdminService {

     public Map selectAllAdmin(String username,Integer page,Integer limit,String sort,String order);
}
