package com.tianyi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tianyi.common.BaseResponse;
import com.tianyi.common.ResultUtils;
import com.tianyi.contant.ErrorCode;
import com.tianyi.domain.User;
import com.tianyi.domain.request.UserLoginRequest;
import com.tianyi.domain.request.UserRegisterRequest;
import com.tianyi.exception.BusinessException;
import com.tianyi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.tianyi.contant.UserConstant.ADMIN_ROLE;
import static com.tianyi.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_RARAM_ERROR);
        }
        String userPassword = userRegisterRequest.getUserPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();

        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest , HttpServletRequest request){
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"用户未登录");
        }
        String userPassword = userLoginRequest.getUserPassword();
        String userAccount = userLoginRequest.getUserAccount();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);

    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout( HttpServletRequest request){
        if (request == null) {
            return null;
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping ("/search")
    public BaseResponse<List<User>> searchUsers(String username ,HttpServletRequest request){
        if(!isAdmin(request))   return ResultUtils.error(null);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        if(StringUtils.isNotBlank(username)){
            userLambdaQueryWrapper.like(User::getUsername,username);
        }
        List<User> list = userService.list(userLambdaQueryWrapper);
        return ResultUtils.success(list);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request){
        if(!isAdmin(request)) return null;
        if(id <= 0){
            return null;
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if(user == null){
            return null;
        }
        // TODO  校验用户是否合法
        user = userService.getById(user.getId());
        user = userService.getSafetyUser(user);
        return ResultUtils.success(user);
    }



    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //鉴权 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
