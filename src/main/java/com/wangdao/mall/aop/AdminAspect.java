/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 17:29
 **/
package com.wangdao.mall.aop;


import com.wangdao.mall.bean.LogDO;
import com.wangdao.mall.service.admin.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Component
@Aspect
public class AdminAspect {

    LogDO logDO;

    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(* com.wangdao.mall.controller.admin..*(..)))")
    public void AdminControllerMypointCut(){}


    @Before("AdminControllerMypointCut()")
    public void mybefore(JoinPoint joinPoint) throws Throwable {
        logDO = new LogDO();
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String admin = (String)request.getSession().getAttribute("admin");
        logDO.setAdmin(admin);
        logDO.setIp(getRemoteIp(request));
        logDO.setAction(getAction(request));
        logDO.setAddTime(new Date(System.currentTimeMillis()));
        logDO.setUpdateTime(new Date(System.currentTimeMillis()));
        logDO.setStatus(true);
        logDO.setDeleted(false);
        logDO.setType(getActionType(request));
        logDO.setResult("成功");
        logDO.setComment("");
//        if (request.getRequestURI().contains("logout")) {
//            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String admin1 = (String) request.getSession().getAttribute("admin");
//            logDO.setAdmin(admin1);
//        }
    }

    /**
     * return 之后
     */
    @AfterReturning("AdminControllerMypointCut()")
    public void myAfterReturning(){
        if (request.getRequestURI().contains("login")){
            String admin = (String) request.getSession().getAttribute("admin");
            logDO.setAdmin(admin);
        }
        if (!request.getMethod().equals("OPTIONS")){
            logService.insertLog(logDO);
        }
    }


    /**
     * 异常日志
     */
    @AfterThrowing("AdminControllerMypointCut()")
    public void myafterThrowing() {
        logDO.setStatus(false);
        String requestURI = request.getRequestURI();
        String admin = (String)request.getSession().getAttribute("admin");
        logDO.setAdmin(admin);
        if (requestURI != null) {
            if (requestURI.contains("login")) {
                logDO.setComment("帐号或密码错误");
                logDO.setResult("登录失败");
            } else if (requestURI.contains("create")) {
                logDO.setComment("创建失败");
                logDO.setComment("参数错误");
            } else if (requestURI.contains("update")) {
                logDO.setComment("修改失败");
                logDO.setComment("参数错误");
            } else if (requestURI.contains("delete")) {
                logDO.setResult("删除失败");
                logDO.setComment("未知异常");
            } else if (requestURI.contains("list")){
                logDO.setResult("查询失败");
                logDO.setComment("查询异常");
            }
        }
        logService.insertLog(logDO);
    }



    /**
     * 获得ip
     * @param request
     * @return ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
            return ip;
        } else {
            ip = ip.split(",")[0];
            return ip;
        }
    }

    /**
     * 获得action
     * @param request
     * @return
     */
    public static String getAction(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login")) {
            return "登录";
        } else if (requestURI.contains("admin/create")) {
            return "创建管理员";
        } else if (requestURI.contains("admin/update")) {
            return "编辑管理员";
        } else if (requestURI.contains("admin/delete")) {
            return "删除管理员";
        }else if (requestURI.contains("role/create")){
            return "创建管理权限";
        }else if (requestURI.contains("role/update")){
            return "编辑管理权限";
        }else if (requestURI.contains("role/delete")){
            return "删除管理权限";
        }else if (requestURI.contains("config/order") && request.getMethod().equals("GET")){
            return "查询订单配置";
        }else if (requestURI.contains("config/order") && request.getMethod().equals("POST")){
            return "编辑订单配置";
        }else if (requestURI.contains("config/express") && request.getMethod().equals("GET")){
            return "查询运费配置";
        }else if (requestURI.contains("config/express") && request.getMethod().equals("POST")){
            return "编辑运费配置";
        }else if (requestURI.contains("config/mall") && request.getMethod().equals("GET")){
            return "查询商城配置" ;
        }else if (requestURI.contains("config/mall") && request.getMethod().equals("POST")){
            return "编辑商城配置";
        }else if (requestURI.contains("config/wx") && request.getMethod().equals("GET")){
            return "查询小程序配置";
        }else if (requestURI.contains("config/wx") && request.getMethod().equals("POST")){
            return "编辑小程序配置";
        }else if (requestURI.contains("create")) {
            return "创建";
        } else if (requestURI.contains("logout")) {
            return "退出";
        } else if (requestURI.contains("update")) {
            return "编辑";
        } else if (requestURI.contains("delete")) {
            return "删除";
        } else if (requestURI.contains("read")) {
            return "查看详情";
        } else {
            return "浏览";
        }
    }

    /**
     * 获得Type类型
     * @param request
     * @return
     */
    public static int getActionType(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login") ||
                requestURI.contains("admin/create") ||
                requestURI.contains("admin/update") ||
                requestURI.contains("admin/delete") ||
                requestURI.contains("role/create")  ||
                requestURI.contains("role/update")  ||
                requestURI.contains("role/delete")  ||
                requestURI.contains("logout")       ||
                requestURI.contains("config/mall")  ||
                requestURI.contains("confix/wx")){
                return 1;
        }else if (requestURI.contains("config/order") || requestURI.contains("config/express")){
            return 2;
        }
        return 0;
    }
}
