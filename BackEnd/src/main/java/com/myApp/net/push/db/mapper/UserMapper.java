package com.myApp.net.push.db.mapper;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.model.user.UpdateInfoModel;
import com.myApp.net.push.utils.Hiber;
import com.myApp.net.push.utils.TextUtil;
import com.mysql.cj.util.StringUtils;

import java.util.List;
import java.util.UUID;

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
