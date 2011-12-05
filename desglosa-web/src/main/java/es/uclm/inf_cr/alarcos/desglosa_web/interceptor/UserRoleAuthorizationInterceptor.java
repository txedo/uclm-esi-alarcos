package es.uclm.inf_cr.alarcos.desglosa_web.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserRoleAuthorizationInterceptor implements Interceptor {
    private static final long serialVersionUID = 4454081807758760289L;

    private String[] authorizedRoles;

    public void destroy() {
    }

    public void init() {
    }

    public void setAuthorizedRoles(String[] authorizedRoles) {
        this.authorizedRoles = authorizedRoles;
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        boolean authorization = false;
        String action = null;
        Authentication authenticatedUser = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authorizedRoles != null) {
            for (int i = 0; i < authenticatedUser.getAuthorities().length
                    && !authorization; i++) {
                String userRol = authenticatedUser.getAuthorities()[i]
                        .getAuthority();
                for (int j = 0; j < authorizedRoles.length && !authorization; j++) {
                    if (authorizedRoles[j].equals(userRol)) {
                        authorization = true;
                        action = invocation.invoke();
                    }
                }
            }
        }
        if (!authorization) {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return action;
    }

}
