/**
 * Created by IntelliJ IDEA
 * User: DB
 * Date:
 * Time: 21:53
 **/
package com.wangdao.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class PermissionsVO {

    private Integer roleId;

    private List<String> permissions;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
