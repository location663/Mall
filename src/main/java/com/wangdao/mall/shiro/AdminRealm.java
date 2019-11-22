package com.wangdao.mall.shiro;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.AdminDOMapper;
import com.wangdao.mall.mapper.PermissionDOMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminDOMapper adminDOMapper;

    @Autowired
    PermissionDOMapper permissionDOMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andDeletedEqualTo(false).andUsernameEqualTo(username);
        List<AdminDO> adminDOS = adminDOMapper.selectByExample(adminDOExample);
        AdminDO adminDO = adminDOS.get(0);  // 就是唯一查出来的 admin
        String passwordFromDb = adminDO.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(adminDO, passwordFromDb, getName());
        return authenticationInfo;
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AdminDO primaryPrincipal = (AdminDO) principalCollection.getPrimaryPrincipal();
        Integer[] roleIds = primaryPrincipal.getRoleIds();
        List<Integer> roleIdsList = Arrays.asList(roleIds);
        PermissionDOExample permissionDOExample = new PermissionDOExample();
        permissionDOExample.createCriteria().andDeletedEqualTo(false).andRoleIdIn(roleIdsList);
        List<PermissionDO> permissionDOList = permissionDOMapper.selectByExample(permissionDOExample);
        ArrayList<String> permissions = new ArrayList<>();
        for (PermissionDO permissionDO : permissionDOList) {
            permissions.add(permissionDO.getPermission());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}
