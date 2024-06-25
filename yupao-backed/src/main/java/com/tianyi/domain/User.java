package com.tianyi.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @TableName user
 */
@TableName(value ="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 头像
     */
    @TableField("avatarUrl")
    private String avatarUrl;

    /**
     * 登录账号
     */
    @TableField("userAccount")
    private String userAccount;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    @TableField("userPassword")
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态
     */
    @TableField("userStatus")
    private Integer userStatus;

    /**
     * 用户权限
     */
    @TableField("userRole")
    private Integer userRole;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 更新 时间
     */
    private Date updateTime;

    /**
     * 星球编号
     */
    @TableField("planetCode")
    private String planetCode;

    /**
     * 逻辑删除
     */
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;



    /**
     * 标签 json 列表
     */
    private String tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(avatarUrl, user.avatarUrl) && Objects.equals(userAccount, user.userAccount) && Objects.equals(gender, user.gender) && Objects.equals(userPassword, user.userPassword) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(userStatus, user.userStatus) && Objects.equals(userRole, user.userRole) && Objects.equals(createTime, user.createTime) && Objects.equals(updateTime, user.updateTime) && Objects.equals(planetCode, user.planetCode) && Objects.equals(isDelete, user.isDelete) && Objects.equals(tags, user.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, avatarUrl, userAccount, gender, userPassword, phone, email, userStatus, userRole, createTime, updateTime, planetCode, isDelete, tags);
    }



}