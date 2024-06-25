package com.tianyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tianyi.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author acer
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-06-25 09:43:17
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




