package com.myApp.net.push.service;

import com.google.common.base.Strings;
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
import java.util.List;

import java.util.stream.Collectors;

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
     * 联系人列表
     */
    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> get_contact() {
        // 拿到自己
        User user = getUser();
        // 拿到我的联系人列表
        List<User> users = UserMapper.get_contacts(user);
        // 转换
        List<UserIdentity> userIdentity = users.stream()
                .map(g_user -> new UserIdentity(g_user, true))
                .collect(Collectors.toList());
        return Response.buildOk(userIdentity);
    }

    // 关注人，双方同时关注
    @PUT
    @Path("/follow/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> follow(@PathParam("followId") String followId) {
        User user = getUser();

        // 不能关注我自己
        if (user.getId().equalsIgnoreCase(followId)) {
            return Response.buildParameterError();
        }
        // 不能为空
        if (Strings.isNullOrEmpty(followId)){
            return Response.buildParameterError();
        }

        // 我关注的人
        User followUser = UserMapper.findById(followId);
        if (followUser == null) {
            // 未找到人
            return Response.buildNotFoundUserError(null);
        }

        // 备注默认没有，后面可以扩展
        followUser = UserMapper.follow(user, followUser, null);
        // 关注失败的情况
        if (followUser == null) {
            return Response.buildServiceError();
        }

        // TODO 通知我关注的人我关注他

        // 返回关注的人的信息
        UserIdentity userIdentity = new UserIdentity(followUser, true);
        return Response.buildOk(userIdentity);
    }


    // 获取某人的信息
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<UserIdentity> getUser(String id) {
        // 返回异常
        if (Strings.isNullOrEmpty(id)) {
            return Response.buildParameterError();
        }
        // 拿到我自己
        User user = getUser();
        // 如果是自己，不必查询数据库
        if (user.getId().equalsIgnoreCase(id)) {
            return Response.buildOk(new UserIdentity(user, true));
        }
        // 通过id寻找
        User g_user = UserMapper.findById(id);
        //没找到用户，返回异常
        if (g_user == null) {
            return Response.buildNotFoundUserError(null);
        }

        // 如果我们直接有关注的记录，则我已关注需要查询信息的用户
        boolean isFollow = UserMapper.getUserFollow(user, g_user) != null;
        return Response.buildOk(new UserIdentity(g_user, isFollow));
    }


    @GET // 搜索人
    @Path("/search/{name:(.*)?}") // 名字为任意字符，可以为空
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<List<UserIdentity>> find(String name) {
        User user = getUser();

        // 找到数据
        List<User> searchUsers = UserMapper.find(name);
        // 得到联系人
        final List<User> contacts = UserMapper.get_contacts(user);
        // 转化
        List<UserIdentity> userCards = searchUsers.stream().map(s_user -> {
                    // 判断这个人是否是我自己，或者是我的联系人中的人，进行联系人的任意匹配，匹配其中的Id字段
                    boolean isFollow = s_user.getId().equalsIgnoreCase(user.getId())
                            || contacts.stream().anyMatch(contactUser -> contactUser.getId().equalsIgnoreCase(s_user.getId()));
                    return new UserIdentity(s_user, isFollow);
        }).collect(Collectors.toList());
        return Response.buildOk(userCards);
    }
}

