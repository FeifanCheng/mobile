package com.myApp.net.push.db.mapper;

import com.google.common.base.Strings;
import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.entity.UserFollow;
import com.myApp.net.push.model.user.UpdateInfoModel;
import com.myApp.net.push.utils.Hiber;
import com.myApp.net.push.utils.TextUtil;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User表操作
 */
public class UserMapper {

    /**
     * @param phone 通过手机号查询用户
     * @return
     */
    public static User findUserByPhone(String phone) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where phone=:mphone")
                .setParameter("mphone", phone)
                .uniqueResult());
    }

    /**
     * @param name 通过名字查询用户
     * @return
     */
    public static User findUserByName(String name) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where name=:mname")
                .setParameter("mname", name)
                .uniqueResult());
    }

    /**
     * 通过账户密码查询用户
     *
     * @param accountNo
     * @param pwd
     * @return
     */
    public static User findUserByAccountAndPwd(String accountNo, String pwd) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where phone=:phone and password =:password")
                .setParameter("phone", accountNo.trim())
                .setParameter("password", encryptPwd(pwd))
                .uniqueResult());
    }

    /**
     * 通过token查询用户
     *
     * @param token
     * @return
     */
    public static User findUserByToken(String token) {
        return Hiber.query(session -> (User) session
                .createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }
    /**
     * 获取我的联系人的列表
     *
     * @param self User
     * @return List<User>
     */
    public static List<User> get_contacts(User self) {
        return Hiber.query(session -> {
            // 重新加载一次用户信息到self中，和当前的session绑定
            session.load(self, self.getId());

            // 所有关注的人
            Set<UserFollow> flows = self.getFollowing();

            //返回UserFollow中getTarget的方法，进行循环
            return flows.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());

        });
    }
    // 通过Name找到User
    public static User findById(String id) {
        // 通过Id查询，更方便
        return Hiber.query(session -> session.get(User.class, id));
    }
    /**
     * 关注人的操作
     *
     * @param origin 发起者
     * @param target 被关注的人
     * @param alias  备注名
     * @return 被关注的人的信息
     */
    public static User follow(final User origin, final User target, final String alias) {
        UserFollow follow = getUserFollow(origin, target);
        if (follow != null) {
            // 已关注，直接返回
            return follow.getTarget();
        }

        return Hiber.query(session -> {
            // 想要操作懒加载的数据，需要重新load一次
            session.load(origin, origin.getId());
            session.load(target, target.getId());

            // 我关注人的时候，同时他增加粉丝信息
            // 所有需要添加两条UserFollow数据
            UserFollow originFollow = new UserFollow();
            originFollow.setOrigin(origin);
            originFollow.setTarget(target);
            // 备注是我对他的备注，他对我默认没有备注
            originFollow.setAlias(alias);

            // 发起者是他，我是被关注的人的记录
            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(target);
            targetFollow.setTarget(origin);

            // 保存数据库
            session.save(originFollow);
            session.save(targetFollow);

            return target;
        });
    }


    /**
     * 查询两个人是否已经关注
     *
     * @param origin 发起者
     * @param target 被关注人
     * @return 返回中间类UserFollow
     */
    public static UserFollow getUserFollow(final User origin, final User target) {
        return Hiber.query(session -> (UserFollow) session
                .createQuery("from UserFollow where originId = :originId and targetId = :targetId")
                .setParameter("originId", origin.getId())
                .setParameter("targetId", target.getId())
                .setMaxResults(1)
                // 唯一查询返回
                .uniqueResult());
    }

    /**
     * 搜索联系人的实现
     *
     * @param name 查询的name，允许为空
     * @return 查询到的用户集合，如果name为空，则返回最近的用户
     */
    @SuppressWarnings("unchecked")
    public static List<User> find(String name) {
        if (Strings.isNullOrEmpty(name))
            name = ""; // 保证不能为null的情况，减少后面的一下判断和额外的错误
        final String searchName = "%" + name + "%"; // 模糊匹配

        return Hiber.query(session -> {
            // 查询的条件：name忽略大小写，并且使用like（模糊）查询；
            // 头像和描述必须完善才能查询到
            return (List<User>) session.createQuery("from User where lower(name) like :name and portrait is not null and description is not null")
                    .setParameter("name", searchName)
                    .setMaxResults(20) // 至多20条
                    .list();

        });

    }


    /**
     * 数据库新增用户
     *
     * @param accountNo 电话
     * @param pwd       密码
     * @param name      名字
     * @return
     */
    public static User addUser(String accountNo, String pwd, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(encryptPwd(pwd));
        user.setPhone(accountNo.trim());

        User savedUser = Hiber.query(session -> {
            session.save(user);
            return user;
        });

        // 如果保存成功，进行登录
        if (savedUser != null) {
            return login(savedUser);
        }
        return null;
    }

    /**
     * 密码加密
     *
     * @param pwd 用户输入的密码
     * @return
     */
    public static String encryptPwd(String pwd) {
        return TextUtil.encodeBase64(TextUtil.getMD5(pwd.trim()));
    }

    /**
     * 账户登录(更新用户token)
     *
     * @param user
     * @return
     */
    public static User login(User user) {
        String token = TextUtil.encodeBase64(UUID.randomUUID().toString());
        user.setToken(token);
        return Hiber.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 绑定设备
     *
     * @param user   需要绑定的user
     * @param pushId 设备id
     * @return
     */
    public static User bindService(User user, String pushId) {
        // 检查是否有别的账户已经绑定这个设备了，若有则解绑
        List<User> bindUsers = Hiber.query(session -> {
            @SuppressWarnings("unchecked")
            List<User> userList = (List<User>) session
                    .createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();
            return userList;
        });
        if (bindUsers != null) {
            for (User bindUser : bindUsers) {
                bindUser.setPushId(null);
                Hiber.query(session -> {
                    session.saveOrUpdate(bindUser);
                    return bindUser;
                });
            }
        }

        // 绑定当前账户
        if (!StringUtils.isNullOrEmpty(user.getPushId())) {
            if (!user.getPushId().equals(pushId)) {
                // TODO: 如果当前账户绑定了别的设备，加一条退出登录的推送
            }
        }
        user.setPushId(pushId);
        return Hiber.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 修改用户信息
     *
     * @param updateInfoModel
     * @param user
     * @return
     */
    public static User updateInfo(UpdateInfoModel updateInfoModel, User user) {
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getDescription())) {
            user.setDescription(updateInfoModel.getDescription());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getPhone())) {
            user.setPhone(updateInfoModel.getPhone());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getPassword())) {
            user.setPassword(updateInfoModel.getPassword());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getPortrait())) {
            user.setPortrait(updateInfoModel.getPortrait());
        }
        if (!StringUtils.isNullOrEmpty(updateInfoModel.getName())) {
            user.setName(updateInfoModel.getName());
        }
        if (updateInfoModel.getSex() != 0) {
            user.setSex(updateInfoModel.getSex());
        }

        return Hiber.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }
}

