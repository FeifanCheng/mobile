package com.myApp.net.push;

import com.myApp.net.push.provider.Authentication;
import com.myApp.net.push.provider.GsonProvider;
import com.myApp.net.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

public class Application extends ResourceConfig {
    public Application(){
        // 注册包名，service包
        packages(AccountService.class.getPackage().getName());
        // 注册json和日志
        register(GsonProvider.class);
        register(Logger.class);
        // 注册拦截器
        register(Authentication.class);

    }

}
