package com.tianyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tianyi.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author  tianyi
* @description 针对表【user】的数据库操作Service
* @createDate 2024-02-26 08:31:02
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount , String userPassword , String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request
     * @return 返回用户信息
     */
    User userLogin(String userAccount , String userPassword , HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    public User getSafetyUser(User user);


    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    public int userLogout(HttpServletRequest request);

    /**
     * 使用SQL查询， 按标签查询用户
     * @param tagNameList
     * @return
     */
    public List<User> searchUsersByTagsSQL(List<String> tagNameList);

    /**
     * 使用内存查询，按标签查询用户
     * @param tagNameList
     * @return
     */
    public List<User> searchUsersByTagsMemory(List<String> tagNameList);
}
