package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.identity.UserIdentity;
import com.myApp.net.push.model.user.UpdateInfoModel;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

// http://localhost:8080/api/user
@Path("/user")
public class UserService {

    @Context
    private SecurityContext securityContext;

    /**
     * 拦截器中会直接返回当时token查好的user
     * @return
     */
    private User getUser(){
        return (User) securityContext.getUserPrincipal();
    }

    /**
     * @param updateInfoModel 需要修改的参数
     * @param token           用户token
     * @return
     */
    @Path("/updateInfo")
    @PUT
    public Response<UserIdentity> updateUserInfo(UpdateInfoModel updateInfoModel,
                                                 @HeaderParam("token") String token) {
        if (UpdateInfoModel.isValid(updateInfoModel)) {
            User user = getUser();
            User updated_user = UserMapper.updateInfo(updateInfoModel, user);
            if(updated_user != null){
                UserIdentity userIdentity = new UserIdentity(updated_user, true);
                return Response.buildOk(userIdentity);
            }
            return Response.buildUserUpdateError();
        }
        return Response.buildParameterError();
    }

}
