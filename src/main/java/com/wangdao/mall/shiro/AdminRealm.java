package com.wangdao.mall.shiro;

import com.wangdao.mall.bean.*;
import com.wangdao.mall.mapper.AdminDOMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminDOMapper adminDOMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        AdminDOExample adminDOExample = new AdminDOExample();
        adminDOExample.createCriteria().andDeletedEqualTo(false).andUsernameNotEqualTo(username);
        List<AdminDO> adminDOS = adminDOMapper.selectByExample(adminDOExample);
        AdminDO adminDO = adminDOS.get(0);
//        LoginDTO user = new LoginDTO();
//        user.setPassword(adminDO.getPassword());
//        user.setUsername(adminDO.getUsername());

        String passwordFromDb = adminDO.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(adminDO, passwordFromDb, getName());

        return authenticationInfo;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AdminDO primaryPrincipal = (AdminDO) principalCollection.getPrimaryPrincipal();
        String username = primaryPrincipal.getUsername();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<String> permissions = adminDOMapper.selectPermissionByUsername(username);

//        authorizationInfo.addStringPermission("user:query");
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}
