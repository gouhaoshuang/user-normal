package com.tianyi.service;

import com.tianyi.domain.User;
import com.tianyi.mapper.UserMapper;
import com.tianyi.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("dogYupi");
        user.setAvatarUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/AMLB8mt9vibR9d6BJyXpxokTf0JR0lpe8bq3oic1Fhw36DwgiaAADYh6ygazQXckvUzbG8JElowp1JZibjvegAMvyA/132");
        user.setUserAccount("123");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("123@qq.com");

        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertEquals(true , save);
    }

    @Autowired
    public  UserServiceImpl userServiceimpl;

//    @Test
//    void testRegister(){
//        User user = new User();
////        //密码不小于8位
//        String userAccount = "123456";
//        String userPassword = "123456";
//        String checkPassword = "123456";
//        String planetCode = "6";
////        long l = userServiceimpl.userRegister(userAccount, userPassword, checkPassword, planetCode);
////        Assertions.assertEquals(-1 , l);
////
////        //账户不包含特殊字符
////        userAccount = "[[]][]][][]";
////        long l1 = userServiceimpl.userRegister(userAccount, userPassword, checkPassword, planetCode);
////        Assertions.assertEquals(-1 , l1);
//
//        //成功插入
//        userAccount = "tianyi168";
//        userPassword = "12345678";
//        checkPassword = "12345678";
//
//        long l3 = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        System.out.println(l3);
//    }
    @Test
    void testLogin(){
        User byId = userService.getById(1);
        System.out.println(byId);
    }

    @Test
    void userRegister() {
    }

    @Test
    void userLogin() {
    }

    @Test
    void getSafetyUser() {
    }

    @Test
    void userLogout() {
    }

    @Test
    void searchUsersByTagsSQL() {
        List<String> list = Arrays.asList("java", "python");
        List<User> usersByTagsSQL = userService.searchUsersByTagsSQL(list);
        Assert.assertNotNull(usersByTagsSQL);
    }

    @Test
    void searchUsersByTagsMemory() {
        List<String> list = Arrays.asList("java", "python");
        List<User> usersByTagsSQL = userService.searchUsersByTagsMemory(list);
        for (User user : usersByTagsSQL) {
            System.out.println(user);
        }
        Assert.assertNotNull(usersByTagsSQL);
    }

}