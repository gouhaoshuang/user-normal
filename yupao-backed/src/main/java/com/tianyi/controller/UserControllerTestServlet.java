package com.tianyi.controller;

import com.tianyi.common.BaseResponse;
import com.tianyi.common.ResultUtils;
import com.tianyi.contant.ErrorCode;
import com.tianyi.domain.User;
import com.tianyi.domain.request.UserLoginRequest;
import com.tianyi.domain.request.UserRegisterRequest;
import com.tianyi.exception.BusinessException;
import com.tianyi.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.tianyi.contant.UserConstant.ADMIN_ROLE;
import static com.tianyi.contant.UserConstant.USER_LOGIN_STATE;

@WebServlet("/user")
public class UserControllerTestServlet extends HttpServlet {


    @Autowired
    private UserService userService;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        switch (pathInfo) {
            case "/register":
//                userRegister(request, response);
                break;
            case "/login":
                userLogin(request, response);
                break;
            case "/logout":
                userLogout(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void userRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserRegisterRequest userRegisterRequest = getUserRegisterRequest(request);
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_RARAM_ERROR);
        }
        String userPassword = userRegisterRequest.getUserPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();

        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);

        BaseResponse<Long> baseResponse = ResultUtils.success(result);
        sendResponse(response, baseResponse);
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserLoginRequest userLoginRequest = getUserLoginRequest(request);
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        String userPassword = userLoginRequest.getUserPassword();
        String userAccount = userLoginRequest.getUserAccount();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        User user = userService.userLogin(userAccount, userPassword, request);

        BaseResponse<User> baseResponse = ResultUtils.success(user);
        sendResponse(response, baseResponse);
    }

    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int result = userService.userLogout(request);

        BaseResponse<Integer> baseResponse = ResultUtils.success(result);
        sendResponse(response, baseResponse);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        if (id <= 0) {
            sendError(response, "Invalid user ID");
            return;
        }

        boolean result = userService.removeById(id);

        BaseResponse<Boolean> baseResponse = ResultUtils.success(result);
        sendResponse(response, baseResponse);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (pathInfo) {
            case "/search":
                searchUsers(request, response);
                break;
            case "/current":
                getCurrentUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void searchUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!isAdmin(request)) {
            sendError(response, "Unauthorized");
            return;
        }

        String username = request.getParameter("username");


//        List<User> userList = userService.list(username);

//        BaseResponse<List<User>> baseResponse = ResultUtils.success(userList);
//        sendResponse(response, baseResponse);
    }

    private void getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            sendError(response, "User not logged in");
            return;
        }

        user = userService.getById(user.getId());
        user = userService.getSafetyUser(user);

        BaseResponse<User> baseResponse = ResultUtils.success(user);
        sendResponse(response, baseResponse);
    }

    private boolean isAdmin(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    private UserRegisterRequest getUserRegisterRequest(HttpServletRequest request) {

        return null;
    }

    private UserLoginRequest getUserLoginRequest(HttpServletRequest request) {
        // Extract UserLoginRequest from HttpServletRequest
        // Example implementation, replace with your actual logic
        return null;
    }

    private void sendResponse(HttpServletResponse response, Object responseObject) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(responseObject.toString());
        out.flush();
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.print(message);
        out.flush();
    }
}
