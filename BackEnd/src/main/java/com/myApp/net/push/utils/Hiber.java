package com.myApp.net.push.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * hibernate初始化
 */
public class Hiber {
    // 全局SessionFactory
    private static SessionFactory sessionFactory;

    static {
        // 静态初始化sessionFactory
        init();
    }

    private static void init() {
        // 从hibernate.cfg.xml文件初始化
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            // build 一个sessionFactory
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // 错误则打印输出，并销毁
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * 获取全局的SessionFactory
     *
     * @return SessionFactory
     */
    public static SessionFactory sessionFactory() {
        return sessionFactory;
    }


    /**
     * 关闭sessionFactory
     */
    public static void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    /**
     * 带返回值的Query接口
     */
    public interface Query<T> {
        T query(Session session);
    }

    /**
     * 请求数据库方法
     */
    public static <T> T query(Query<T> query) {
        Session session = sessionFactory().openSession();

        T t = null;
        Transaction transaction = session.beginTransaction();
        try {
            t = query.query(session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            session.close();
        }
        return t;
    }

}
