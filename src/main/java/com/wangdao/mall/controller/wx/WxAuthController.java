package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.service.util.encryptutil.Md5Utils;
import com.wangdao.mall.service.wx.WxUserService;
import com.wangdao.mall.shiro.CustomToken;
import org.apache.ibatis.annotations.Param;
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
//        CustomToken token = new CustomToken(userDO.getUsername(), Md5Utils.getMultiMd5(userDO.getPassword()), "wx");
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

    /**
     * 用户个人页面
     * @return
     */
    @RequestMapping("user/index")
    public BaseReqVo userIndex(){
        Map map = userService.userIndex();
        return new BaseReqVo(map, "成功", 0);
    }

    /**
     * 用户注册
     * @return
     */
    @RequestMapping("auth/register")
    public BaseReqVo userRegister(@RequestBody UserDO userDO) throws Exception {
        Map map = userService.userRegister(userDO);
        Subject subject = SecurityUtils.getSubject();
//        CustomToken token = new CustomToken(userDO.getUsername(), Md5Utils.getMultiMd5(userDO.getPassword()), "wx");
        CustomToken token = new CustomToken(userDO.getUsername(), userDO.getPassword(), "wx");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return new BaseReqVo(null, "登录失败", 500);
        }
        userService.login();
//        map1.put("token", request.getSession().getId());
        return new BaseReqVo(map, "成功", 0);
    }

    /**
     * 获取验证码
     * @return
     */
    @RequestMapping("auth/regCaptcha")
    public BaseReqVo regCaptcha(@RequestBody Map<String, String> map) throws WxException {
        String mobile = map.get("mobile");
        if (!mobile.matches("^1[3|4|5|7|8][0-9]\\\\d{4,8}$")) {
            throw new WxException("您输入的手机号有误，请重新输入");
        }
        userService.getRegCaptcha(mobile);
        return new BaseReqVo(null, "成功", 0);
    }

    @RequestMapping("filter/redirect")
    public BaseReqVo filterRedirect()  {
        return new BaseReqVo(null, "您还未登陆", 501);
    }
}
