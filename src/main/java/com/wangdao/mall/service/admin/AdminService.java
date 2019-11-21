package com.wangdao.mall.service.admin;

import com.wangdao.mall.bean.*;

import java.util.List;
import java.util.Map;

public interface AdminService {

     Map selectAllAdmin(String username,Integer page,Integer limit,String sort,String order);

     List<Map> selectAllAdminRole();

     AdminDO createNewAdmin(AdminDO adminDO);

     AdminDO updateAdmin(AdminDO adminDO);

     int deleteAdmin(AdminDO adminDO);

     Map<String, Object> selectAllLogList(String name,Integer page, Integer limit, String sort, String order);

     Map<String, Object> selectAllRoleList(String name,Integer page, Integer limit, String sort, String order);

     RoleDO createNewRole(RoleDO roleDO);

     RoleDO updateRole(RoleDO roleDO);

     int deleteRole(RoleDO roleDO);

     Map<String, Object> selectAllStorageList(String name, String key, Integer page, Integer limit, String sort, String order);

     int deleteStorage(StorageDO storageDO);

     StorageDO updateStorage(StorageDO storageDO);

     List<String> listPermissions(Integer roleId);

     void updateRolePermissions(PermissionsVO permissionsVO);

     List<SystemPermissionDO> systemPermissionsList();

     List<String> selectPermsLeft(Integer roleId);

     String selectRoleName(Integer roleId);
}
