package com.tianyi;

import com.tianyi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserCenterApplicationTests {





    @Autowired
    private UserMapper userMapper;

//    @Test
//    void contextLoads() {
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        Assert.isTrue(5 == userList.size(), "");
//        userList.forEach(System.out::println);
//    }

}
