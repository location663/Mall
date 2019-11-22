package com.wangdao.mall.controller.wx;

import com.wangdao.mall.bean.BaseReqVo;
import com.wangdao.mall.bean.UserDO;
import com.wangdao.mall.exception.WxException;
import com.wangdao.mall.service.util.wx.BaseRespVo;
import com.wangdao.mall.service.util.wx.UserInfo;
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
//        CustomToken token = new CustomToken(userDO.getUsername(), Md5Utils.getMultiMd5(userDO.getPassword()), "wx");
        CustomToken token = new CustomToken(userDO.getUsername(), userDO.getPassword(), "wx");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return new BaseReqVo(null, "登录失败", 500);
        }
        Map map = userService.login(request.getRemoteAddr());
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
    public BaseReqVo userRegister(@RequestBody UserDO userDO, HttpServletRequest request) throws Exception {
        Map map = userService.userRegister(userDO);
        Subject subject = SecurityUtils.getSubject();
//        CustomToken token = new CustomToken(userDO.getUsername(), Md5Utils.getMultiMd5(userDO.getPassword()), "wx");
        CustomToken token = new CustomToken(userDO.getUsername(), userDO.getPassword(), "wx");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return new BaseReqVo(null, "登录失败", 500);
        }
        userService.login(request.getRemoteAddr());
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
        if (!mobile.matches("^1[3,4,5,7,8][0-9]\\d{4,8}$")) {
            throw new WxException("您输入的手机号有误，请重新输入");
        }
        userService.getRegCaptcha(mobile);
        return new BaseReqVo(null, "成功", 0);
    }


    /**重置账户密码，默认根据手机号来唯一标识账户，但数据库里老师留了很多账号手机号一样，不用这些数据
     * @return
     */
    @RequestMapping("auth/reset")
    public BaseRespVo resetPassword(@RequestBody Map<String, String> map) throws WxException {
        /*获取短信验证码
         * 密码MD5加密
         * 存入数据库
         * */
        String mobile = map.get("mobile");
        String code = map.get("code");
        String password = map.get("password");
        if (!mobile.matches("^[1][3,4,5,7,8][0-9]{9}$")) {
            throw new WxException("您输入的手机号有误，请重新输入");
        }
        int reset = userService.resetPassword(mobile, code, password);
        if (reset != 1) {
            throw new WxException("系统繁忙，请稍后再试");
        }
        return BaseRespVo.ok("重置密码成功");
    }

    @RequestMapping("filter/redirect")
    public BaseReqVo filterRedirect()  {
        return new BaseReqVo(null, "您还未登陆", 501);
    }

    @RequestMapping("auth/login_by_weixin")
    public BaseRespVo loginnByWeixin(@RequestBody Map<String, Object> map){
        String code = (String) map.get("code");
        System.out.println(code);
        Map userInfoMap = (Map) map.get("userInfo");
        for (Object s : userInfoMap.keySet()) {
            Object o = userInfoMap.get(s);
            System.out.println(o);
        }
        userService.loginnByWeixin(userInfoMap, code);
        return BaseRespVo.fail(-1, "错误");
    }
}
