package com.myApp.net.push.provider;

import com.myApp.net.push.db.entity.User;
import com.myApp.net.push.db.mapper.UserMapper;
import com.myApp.net.push.response.Response;
import com.mysql.cj.util.StringUtils;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;

/**
 * 验证请求是否登录过了
 */
public class Authentication implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // 验证是否是登录和注册
        String path = ((ContainerRequest) requestContext).getPath(false);
        if (path.startsWith("account/login") || path.startsWith("account/register")) {
            return;
        }

        // 看下token是不是正确
        String token = requestContext.getHeaders().getFirst("token");
        if (!StringUtils.isNullOrEmpty(token)) {
            User user = UserMapper.findUserByToken(token);
            if (user != null) {
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return user;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
                return;
            }
        }

        // 如果被拦截了，直接返回一个错误
        Response<Object> error = Response.buildAccountError();
        requestContext.abortWith(javax.ws.rs.core.Response.status(200)
                .entity(error)
                .build());
    }
}
