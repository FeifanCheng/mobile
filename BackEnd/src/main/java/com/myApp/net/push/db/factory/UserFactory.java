package com.myApp.net.push.db.factory;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.utils.Hiber;
import com.myApp.net.push.utils.TextUtil;

/**
 * User数据库操作
 */
public class UserFactory {

    /**
     *
     * @param phone 通过手机号查询用户
     * @return
     */
    public static User findUserByPhone(String phone){
        return Hiber.query(session -> (User) session
                .createQuery("from User where phone=:mphone")
                .setParameter("mphone", phone)
                .uniqueResult());
    }

    /**
     *
     * @param name 通过名字查询用户
     * @return
     */
    public static User findUserByName(String name){
        return Hiber.query(session -> (User) session
                .createQuery("from User where name=:mname")
                .setParameter("mname", name)
                .uniqueResult());
    }

    /**
     * 数据库新增用户
     * @param accountNo 电话
     * @param pwd 密码
     * @param name 名字
     * @return
     */
    public static User addUser(String accountNo, String pwd, String name){
        User user = new User();
        user.setName(name);
        user.setPassword(encryptPwd(pwd));
        user.setPhone(accountNo.trim());
        return Hiber.query(session -> {
            session.save(user);
            return user;
        });
    }

    /**
     * 密码加密
     * @param pwd 用户输入的密码
     * @return
     */
    public static String encryptPwd(String pwd){
        return TextUtil.encodeBase64(TextUtil.getMD5(pwd.trim()));
    }

}
