package com.tianyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianyi.contant.ErrorCode;
import com.tianyi.domain.User;
import com.tianyi.exception.BusinessException;
import com.tianyi.mapper.UserMapper;
import com.tianyi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.tianyi.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
* @author tianyi
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-02-26 08:31:02
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService{

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "tianyi";


    @Autowired
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //1. 校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名或密码不能为空");
        }
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名长度过短");
        }
        if(planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
        if(userPassword.length() < 8 || checkPassword.length() < 8 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度不对");
        }
        //账户不能包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符，则返回
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户不能包含特殊字符");
        }

        //账户不能重复
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserAccount, userAccount);
        int count = this.count(lambdaQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }
        //星球编号不能重复
        lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPlanetCode, planetCode);
        count = this.count(lambdaQueryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复");
        }
        //密码和校验密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验密码不相同");
        }
        //2. 校验密码是否输入正确，要和数据库中的密文密码去对比
        String encodePassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encodePassword);
        user.setPlanetCode(planetCode);
        boolean save = this.save(user);
        if(!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"数据库保存失败");
        }
        return user.getId();
    }


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1. 校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            //todo 修改为自定义异常
            return null;
        }
        if(userAccount.length() < 4){
            return null;
        }
        if(userPassword.length() < 8){
            return null;
        }
        //账户不能包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符，则返回
        if(matcher.find()){
            return null;
        }

        //2. 加密
        String encodePassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        LambdaQueryWrapper<User> lwq = new LambdaQueryWrapper<>();
        lwq.eq(User::getUserAccount , userAccount);
        lwq.eq(User::getUserPassword, encodePassword);

        User user = userMapper.selectOne(lwq);
        //用户不存在
        if(user == null){
            log.info("user login failed , userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.NOT_LOGIN,"用户账号或密码错误");
        }
        //3.用户脱敏
        User safetyUser = this.getSafetyUser(user);

        //4. 记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }

    public User getSafetyUser(User user){
        if(user == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setPlanetCode(user.getPlanetCode());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setTags(user.getTags());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return  1;
    }


    public List<User> searchUsersByTagsSQL(List<String> tagNameList){
        if(CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        for (String tag : tagNameList) {
            queryWrapper.like(User::getTags,tag);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        return users.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }
    public List<User> searchUsersByTagsMemory(List<String> tagNameList){
        if(CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Gson gson = new Gson();
        List<User> users = userMapper.selectList(null);
        return users.stream().filter(user ->{
            String tags = user.getTags();
            if(StringUtils.isBlank(tags)){
                return false;
            }
            Set<String> tempTagNameSet = gson.fromJson(tags, new TypeToken<Set<String>>() {}.getType());
            for (String tagName : tagNameList) {
                if(!tempTagNameSet.contains(tagName)){
                    return false;
                }
            }
            return true;
        }).map(this::getSafetyUser).collect(Collectors.toList());
    }


}




