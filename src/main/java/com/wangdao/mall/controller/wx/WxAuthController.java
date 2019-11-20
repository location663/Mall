package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.service.wx.WxUserService;
import com.wangdao.mall.shiro.CustomToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxAuthController {

    @Autowired
    WxUserService userService;

    /**
     * 微信用户登录
     * @param userDO
     * @param request
     * @return
     */
    @RequestMapping("auth/login")
    public BaseReqVo wxLogin(@RequestBody UserDO userDO, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        CustomToken token = new CustomToken(userDO.getUsername(), userDO.getPassword(), "wx");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return new BaseReqVo(null, "登录失败", 500);
        }
        Map map = userService.login();
        map.put("token", request.getSession().getId());

        return new BaseReqVo(map, "成功", 0);
    }

    /**
     * 用户退出登录
     * @return
     */
    @RequestMapping("auth/logout")
    public BaseReqVo wxLogout(){
        SecurityUtils.getSubject().logout();
        return new BaseReqVo(null, "成功", 0);
    }

    @RequestMapping("user/index")
    public BaseReqVo userIndex(){
        Map map = userService.userIndex();
        return new BaseReqVo(map, "成功", 0);
    }
}
