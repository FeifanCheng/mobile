package com.myApp.net.push.service;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.identity.UserIdentity;
import com.myApp.net.push.model.user.UpdateInfoModel;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        assert securityContext != null;
        return (User) securityContext.getUserPrincipal();
    }

    /**
     * @param updateInfoModel 需要修改的参数
     * @param token           用户token
     * @return
     */
    @Path("/updateInfo")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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

    /**
     * 获取一个人的信息
     * @return
     */
    @Path("/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> getUser(@PathParam("id") String id){
        if(StringUtils.isNullOrEmpty(id)){
            return Response.buildParameterError();
        }

        User my = getUser();
        if(Objects.equals(my.getId(), id)){
            return Response.buildOk(new UserIdentity(my, true));
        }
        User find = UserMapper.findUserByID(id);
        if(find == null){
            return Response.buildNotFoundUserError(null);
        }
        if(UserMapper.isFollow(my, find) != null){
            return Response.buildOk(new UserIdentity(find, true));
        }
        return Response.buildOk(new UserIdentity(find, false));
    }

    /**
     * 获取所有联系人
     * @return
     */
    @Path("/contacts")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> getContacts(){
        List<User> contacts = UserMapper.findContacts(getUser());
        List<UserIdentity> contactIdentityList = new ArrayList<>();
        for (User contact : contacts) {
            contactIdentityList.add(new UserIdentity(contact, true));
        }
        return Response.buildOk(contactIdentityList);
    }

    /**
     * 搜索一个人(名字可以为空)
     * @return
     */
    @Path("/find/{name:(.*)?}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> findUser(@DefaultValue("") @PathParam("name") String name){
        User my = getUser();
        // 如果名字为空，返回数据库前10个user; 否则进行匹配，并显示是否已经关注
        List<User> userList = UserMapper.findUsersByName(name);
        if(userList == null){
            return Response.buildNotFoundUserError("No matched users found!!");
        }

        List<UserIdentity> userIdentityList = new ArrayList<>();
        for (User follow : userList) {
            if(UserMapper.isFollow(my, follow) != null){
                userIdentityList.add(new UserIdentity(follow, true));
            } else {
                userIdentityList.add(new UserIdentity(follow, false));
            }
        }
        return Response.buildOk(userIdentityList);
    }

    /**
     * 关注一个人
     * @return
     */
    @Path("/follow/{followId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> follow(@PathParam("followId") String followId){
        User user = getUser();
        if(Objects.equals(followId, user.getId())){
            return Response.buildParameterError();
        }

        User following = UserMapper.findUserByID(followId);
        if(following != null){
            following = UserMapper.followContact(user, following, null);
            if(following == null){
                return Response.buildServiceError();
            }
            // TODO: 通知一下关注人
            return Response.buildOk(new UserIdentity(following, true));
        }
        return Response.buildNotFoundUserError("This user doesn't exist");
    }

}
