/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 17:11
 **/
package com.wangdao.mall.service.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDOMapper adminDOMapper;

    @Autowired
    RoleDOMapper roleDOMapper;

    @Autowired
    LogDOMapper logDOMapper;

    @Autowired
    StorageDOMapper storageDOMapper;

    @Autowired
    PermissionDOMapper permissionDOMapper;

    @Autowired
    SystemPermissionDOMapper systemPermissionDOMapper;

    @Override
    public Map selectAllAdmin(String username,Integer page,Integer limit,String sort,String order) {
        AdminDOExample adminDOExample = new AdminDOExample();
        PageHelper.startPage(page,limit);
        AdminDOExample.Criteria criteria = adminDOExample.createCriteria();
        if (username != null){
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);
        adminDOExample.setOrderByClause(sort + " " + order);
        List<AdminDO> adminDOList = adminDOMapper.selectByExample(adminDOExample);
        PageInfo<AdminDO> userPageInfo = new PageInfo<>(adminDOList);
        long total = userPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", adminDOList);
        return map;
    }

    @Override
    public List<Map> selectAllAdminRole() {
        List<Map> list = new ArrayList<>();
        RoleDOExample roleDOExample = new RoleDOExample();
        roleDOExample.createCriteria().andDeletedEqualTo(false);
        List<RoleDO> roleDOS = roleDOMapper.selectByExample(roleDOExample);
        for (RoleDO roleDO : roleDOS) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("value",roleDO.getId());
            map.put("label", roleDO.getName());
            list.add(map);
        }
        return list;
    }

    /**
     * username校验  √
     * 数据库没有添加 add_time          √
     *              update_time 完成  √
     * 新增 ： 判断用户名长度过短         √
     * @param adminDO
     * @return
     */
    @Override
    public AdminDO createNewAdmin(AdminDO adminDO) {
        AdminDOExample adminDOExampleForCheckUsername = new AdminDOExample();  // 作username重名校验
        adminDOExampleForCheckUsername.createCriteria().andDeletedEqualTo(false);
        List<AdminDO> adminDOListForCheckUsername = adminDOMapper.selectByExample(adminDOExampleForCheckUsername);
        for (AdminDO aDo : adminDOListForCheckUsername) {
            if (adminDO.getUsername().equals(aDo.getUsername())){
                return null;
            }
        }

        adminDO.setAddTime(new Date(System.currentTimeMillis()));
        adminDO.setUpdateTime(new Date(System.currentTimeMillis()));
        adminDO.setLastLoginTime(new Date(System.currentTimeMillis()));
        int i = adminDOMapper.insertSelective(adminDO);      // 做 “增” 的操作
        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andUsernameEqualTo(adminDO.getUsername()).andPasswordEqualTo(adminDO.getPassword()).andDeletedEqualTo(false);
        if (i != 0){
            int id = adminDOMapper.selectLastInsertAdminId();
            AdminDO adminDOAfterCreate = adminDOMapper.selectByPrimaryKey(id);
            return adminDOAfterCreate;
        }
        return null;
    }

    /**
     * 如果使用 updateByPrimarykey() 会把所有的字段给赋默认 例如：把add_time给覆盖掉
     * 现在使用 updateByPrimaryKeySelective() 只会更新想更新的值     √
     *
     * 问题更新：这里也要有重名校验  但这里似乎有点不一样啊  这里需要添加两个条件 1、id相同 2、名字重名
     *
     * @param adminDO
     * @return
     */
    @Override
    public AdminDO updateAdmin(AdminDO adminDO) {
        AdminDOExample adminDOExampleForCheckUsername = new AdminDOExample();  // 作username重名校验
        adminDOExampleForCheckUsername.createCriteria().andDeletedEqualTo(false);
        List<AdminDO> adminDOListForCheckUsername = adminDOMapper.selectByExample(adminDOExampleForCheckUsername);
        for (AdminDO aDo : adminDOListForCheckUsername) {
            if (adminDO.getUsername().equals(aDo.getUsername()) && adminDO.getId() != aDo.getId()){
                return null;
            }
        }

        int update = adminDOMapper.updateByPrimaryKeySelective(adminDO);  // 选择更新 建议使用selective
        if (update != 0){
            AdminDO adminDOAfterUpdate  = adminDOMapper.selectByPrimaryKey(adminDO.getId());
            return adminDOAfterUpdate;
        }
        return null;
    }

    @Override
    public int deleteAdmin(AdminDO adminDO) {
        adminDO.setDeleted(true);
        int update = adminDOMapper.updateByPrimaryKeySelective(adminDO);
        return update;
    }

    @Override
    public Map<String, Object> selectAllLogList(String name,Integer page, Integer limit, String sort, String order) {
        LogDOExample logDOExample = new LogDOExample();
        LogDOExample.Criteria criteria = logDOExample.createCriteria();
        PageHelper.startPage(page,limit);
        if (name != null){
            criteria.andAdminLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        logDOExample.setOrderByClause(sort + " " + order);
        List<LogDO> logDOList = logDOMapper.selectByExample(logDOExample);
        PageInfo<LogDO> logDOPageInfo = new PageInfo<>(logDOList);
        long total = logDOPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", logDOList);
        return map;
    }

    @Override
    public Map<String, Object> selectAllRoleList(String name,Integer page, Integer limit, String sort, String order) {
        RoleDOExample roleDOExample = new RoleDOExample();
        RoleDOExample.Criteria criteria = roleDOExample.createCriteria();
        PageHelper.startPage(page,limit);
        if (name != null){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);
        roleDOExample.setOrderByClause(sort + " " + order);
        List<RoleDO> roleDOList = roleDOMapper.selectByExample(roleDOExample);
        PageInfo<RoleDO> roleDOPageInfo = new PageInfo<>(roleDOList);
        long total = roleDOPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", roleDOList);
        return map;
    }

    /**
     * 数据库中的 name 已经是 unique
     * @param roleDO
     * @return
     */
    @Override
    public RoleDO createNewRole(RoleDO roleDO) {
        RoleDOExample roleDOExample = new RoleDOExample();
        roleDOExample.createCriteria();
        List<RoleDO> roleDOListForCheckName = roleDOMapper.selectByExample(roleDOExample);
        for (RoleDO aDo : roleDOListForCheckName) {
            if (roleDO.getName().equals(aDo.getName())){
                return null;
            }
        }
        roleDO.setAddTime(new Date(System.currentTimeMillis()));
        roleDO.setUpdateTime(new Date(System.currentTimeMillis()));
        int insertResult = roleDOMapper.insertSelective(roleDO);
        if (insertResult != 0) {
            int idLastInsert = roleDOMapper.selectLastInsertRoleId();
            RoleDO roleDOAfterInsert = roleDOMapper.selectByPrimaryKey(idLastInsert);
            return roleDOAfterInsert;
        }
        return null;
    }

    @Override
    public RoleDO updateRole(RoleDO roleDO) {
        RoleDOExample roleDOExample = new RoleDOExample();
        List<RoleDO> roleDOList = roleDOMapper.selectByExample(roleDOExample);
        for (RoleDO aDo : roleDOList) {
            if (roleDO.getName().equals(aDo.getName()) && roleDO.getId() != aDo.getId()){
                return null;
            }
        }

        int update = roleDOMapper.updateByPrimaryKeySelective(roleDO);
        if (update != 0){
            RoleDO roleDOAfterUpdate = roleDOMapper.selectByPrimaryKey(roleDO.getId());
            return roleDOAfterUpdate;
        }
        return null;
    }

    @Override
    public int deleteRole(RoleDO roleDO) {
        roleDO.setDeleted(true);
        int update = roleDOMapper.updateByPrimaryKeySelective(roleDO);
        PermissionDOExample permissionDOExample = new PermissionDOExample();
        permissionDOExample.createCriteria().andRoleIdEqualTo(roleDO.getId());
        permissionDOMapper.deleteByExample(permissionDOExample);
        return update;
    }

    /**
     *  还有问题
     * @param name
     * @param key
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> selectAllStorageList(String name, String key, Integer page, Integer limit, String sort, String order) {
        StorageDOExample storageDOExample = new StorageDOExample();
        PageHelper.startPage(page,limit);
        StorageDOExample.Criteria criteria = storageDOExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (name != null){                                                          // 有问题
            criteria.andNameLike("%" + name + "%");
        }
        if (key != null){
            criteria.andKeyLike("%" + key + "%");
        }
        storageDOExample.setOrderByClause(sort + " " + order);
        List<StorageDO> storageDOList = storageDOMapper.selectByExample(storageDOExample);
        PageInfo<StorageDO> storageDOPageInfo = new PageInfo<>(storageDOList);
        long total = storageDOPageInfo.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("items", storageDOList);
        return map;
    }

    @Override
    public int deleteStorage(StorageDO storageDO) {
        storageDO.setDeleted(true);
        int update = storageDOMapper.updateByPrimaryKeySelective(storageDO);
        return update;
    }

    @Override
    public StorageDO updateStorage(StorageDO storageDO) {
        int update = storageDOMapper.updateByPrimaryKeySelective(storageDO);
        if (update != 0){
            StorageDO storageDOAfterUpdate = storageDOMapper.selectByPrimaryKey(storageDO.getId());
            return storageDOAfterUpdate;
        }
        return null;
    }

    @Override
    public List<String> listPermissions(Integer roleId) {
        PermissionDOExample permissionDOExample = new PermissionDOExample();
        PermissionDOExample.Criteria criteria = permissionDOExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<PermissionDO> permissionDOSList = permissionDOMapper.selectByExample(permissionDOExample);
        List<String> perlist = new ArrayList<>();
        for (PermissionDO permissionDO : permissionDOSList) {
            if (!permissionDO.getPermission().equals("*")){
                perlist.add(permissionDO.getPermission());
            }else {
                SystemPermissionDOExample systemPermissionDOExample = new SystemPermissionDOExample();
                SystemPermissionDOExample.Criteria criteria1 = systemPermissionDOExample.createCriteria();
                criteria1.andLevelEqualTo("3");
                List<SystemPermissionDO> systemPermissionDOS = systemPermissionDOMapper.selectByExample(systemPermissionDOExample);
                for (SystemPermissionDO systemPermissionDO : systemPermissionDOS) {
                    perlist.add(systemPermissionDO.getId());
                }
            }
        }
        return perlist;
    }

    /**
     * 此方法与上面的方法共同完成显示
     * @return
     */
    @Override
    public List<SystemPermissionDO> systemPermissionsList() {
        SystemPermissionDOExample systemPermissionDOExample = new SystemPermissionDOExample();
        List<SystemPermissionDO> systemPermissionDOList = systemPermissionDOMapper.selectByExample(systemPermissionDOExample);
        List<SystemPermissionDO> systemPermissionsList1 = new ArrayList<>();
        List<SystemPermissionDO> systemPermissionsList2 = new ArrayList<>();
        List<SystemPermissionDO> systemPermissionsList3 = new ArrayList<>();
        for (SystemPermissionDO systemPermissionDO : systemPermissionDOList) {
            if (systemPermissionDO.getLevel().equals("3")){
                systemPermissionsList3.add(systemPermissionDO);
            }
        }
        for (SystemPermissionDO systemPermissionDO : systemPermissionDOList) {
            if (systemPermissionDO.getLevel().equals("2")){
                systemPermissionsList2.add(systemPermissionDO);
            }
        }
        for (SystemPermissionDO systemPermissionDO : systemPermissionDOList) {
            if (systemPermissionDO.getLevel().equals("1")){
                systemPermissionsList1.add(systemPermissionDO);
            }
        }

        for (SystemPermissionDO systemPermission3 : systemPermissionsList3) {
            for (SystemPermissionDO systemPermission2 : systemPermissionsList2) {
                if (systemPermission3.getpId().equals(systemPermission2.getsId())){
                    systemPermission2.getChildren().add(systemPermission3);
                }
            }
        }
        for (SystemPermissionDO permission2 : systemPermissionsList2) {
            for (SystemPermissionDO permission1 : systemPermissionsList1) {
                if (permission2.getpId().equals(permission1.getsId())){
                    permission1.getChildren().add(permission2);
                }
            }
        }
        return systemPermissionsList1;
    }

    /**
     * 更新权限
     * @param permissionsVO
     * @return
     */
    @Override
    public void updateRolePermissions(PermissionsVO permissionsVO) {
        PermissionDOExample permissionDOExample = new PermissionDOExample();
        PermissionDOExample.Criteria criteria = permissionDOExample.createCriteria();
        criteria.andRoleIdEqualTo(permissionsVO.getRoleId());
        permissionDOMapper.deleteByExample(permissionDOExample);
        List<String> permissions = permissionsVO.getPermissions();
        for (String permission : permissions) {
            PermissionDO permissionDO = new PermissionDO();
            permissionDO.setRoleId(permissionsVO.getRoleId());
            permissionDO.setPermission(permission);
            permissionDO.setAddTime(new Date(System.currentTimeMillis()));
            permissionDO.setUpdateTime(new Date(System.currentTimeMillis()));
            permissionDO.setDeleted(false);
            permissionDOMapper.insert(permissionDO);
        }
    }

    @Override
    public List<String> selectPermsLeft(Integer roleId) {
        List<String> permsList = adminDOMapper.selectPermsLeft(roleId);
        return permsList;
    }

    @Override
    public String selectRoleName(Integer roleId) {
        String roleName = adminDOMapper.selectRoleName(roleId);
        return roleName;
    }

    @Override
    public int updateLoginTimeAndIp(AdminDO adminDO,String remoteIp) {
        String username = adminDO.getUsername();
        String password = adminDO.getPassword();
        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password).andDeletedEqualTo(false);
        AdminDO adminDO1 = new AdminDO();
        adminDO1.setLastLoginTime(new Date());
        adminDO1.setLastLoginIp(remoteIp);
        int i = adminDOMapper.updateByExampleSelective(adminDO1,adminDOExample);
        return i;
    }

    @Override
    public List<AdminDO> selectAdmin() {
        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andDeletedEqualTo(false);
        List<AdminDO> adminDOList = adminDOMapper.selectByExample(adminDOExample);
        return adminDOList;
    }

}
