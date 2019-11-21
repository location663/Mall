package com.wangdao.mall.shiro;

import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.bean.UserDOExample;
import com.wangdao.mall.mapper.UserDOMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WxRealm extends AuthorizingRealm {

    @Autowired
    UserDOMapper userMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        UserDOExample userDOExample = new UserDOExample();
        userDOExample.createCriteria().andStatusEqualTo((byte) 0).andUsernameEqualTo(username);
        List<UserDO> userDOS = userMapper.selectByExample(userDOExample);
        UserDO userDO = userDOS.get(0);
        String passwordFromDb = userDO.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userDO, passwordFromDb, getName());
        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        UserDO primaryPrincipal = (UserDO) principalCollection.getPrimaryPrincipal();
//        String username = primaryPrincipal.getUsername();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        List<String> permissions = userMapper.selectPermissionByUsername(username);

//        authorizationInfo.addStringPermission("user:query");
//        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}
