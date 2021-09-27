package com.myApp.net.push.service;

import com.google.gson.annotations.Expose;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.factory.UserFactory;
import com.myApp.net.push.db.identity.UserIdentity;
import com.myApp.net.push.model.account.RegisterModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/api/account
@Path("/account")
public class AccountService {

//    @Path("/test")
//    @GET
//    public String get(){
//        User user = UserFactory.findUser("123456");
//        if(user == null){
//            return "ok";
//        }
//        return "wrong";
//    }

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
    public UserIdentity register(RegisterModel registerModel) {
        // TODO：需要提前告知用户是否已经有用户名存在（phone,name不能重合)
        if(UserFactory.findUserByPhone(registerModel.getAccountNo()) != null){
            return null;
        }

        if(UserFactory.findUserByName(registerModel.getName()) != null){
            return null;
        }

        User user = UserFactory.addUser(registerModel.getAccountNo(),
                registerModel.getPwd(), registerModel.getName());
        if(user != null){
            UserIdentity userIdentity = new UserIdentity();
            userIdentity.setPhone(registerModel.getAccountNo());
            userIdentity.setIsFollow(true);
            userIdentity.setName(registerModel.getName());
            userIdentity.setUpdateAt(user.getUpdateAt());
            return userIdentity;
        }
        return null;
    }
}
