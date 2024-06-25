package com.tianyi.domain.request;

import lombok.Data;

import java.io.Serializable;



@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3L;

    private String userAccount;
    private String userPassword;

}
