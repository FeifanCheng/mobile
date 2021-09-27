package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// localhost:8080/api/account
@Path("/account")
public class AccountService {

    @GET
    @Path("/test")
    public String get() {
        return "success";
    }

    //POST 127.0.0.1/api/account/login
    @POST
    @Path("/login")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post() {
        User user = new User();
        user.setName("美女");
        user.setSex(2);
        return user;
    }

}
