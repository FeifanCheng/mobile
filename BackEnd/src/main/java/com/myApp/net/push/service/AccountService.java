package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.model.account.RegisterModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/api/account
@Path("/account")
public class AccountService {

    /**
     *
     * @param registerModel 请求参数：pwd，name，accountNo
     * @return
     */
    @POST
    @Path("/register")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(RegisterModel registerModel) {
        return new User();
    }

}
