package com.pgl.security;

import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.Filter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.pgl.models.ApplicationClient;
import com.pgl.models.FinancialInstitution;
import com.pgl.models.User;
import com.pgl.services.UserService;
import com.pgl.utils.ContextName;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This filter verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private UserService userService;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    private static User user;
    private String context = null;

    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        Method method = resourceInfo.getResourceMethod();

        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Access blocked for all users !!").build());
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //Fetch context path
            context= (String) requestContext.getProperty("contextName");

            //If no authorization information present; block access
            if(authorization == null || authorization.isEmpty())
            {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
                return;
            }

            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));;

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String login = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verifying Username and password
            System.out.println(login);
            System.out.println(password);

            requestContext.getHeaders().add("login", login);
            //Verify user access
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid ?
                if( ! isUserAllowed(login, password, rolesSet))
                {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You cannot access this resource").build());
                    return;
                }

                // Account is activate ?
                if(user.getActive() != true){
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Account not activated").build());
                    return;
                }
            }
        }

    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {
        boolean isAllowed = false;

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);

        if (context == ContextName.CLIENT.toString()){
            userService = new UserService(ApplicationClient.class);
            ApplicationClient applicationClient = (ApplicationClient) userService.findUserById(username);
            user.setPassword(applicationClient.getPassword());
        }else if (context == ContextName.INSTITUTION.toString()) {
            userService = new UserService(FinancialInstitution.class);
            FinancialInstitution financialInstitution = (FinancialInstitution) userService.findUserById(username);
            user.setPassword(financialInstitution.getPassword());
        }

        if(user != null && bCryptPasswordEncoder.matches(password, user.getPassword()))
        {
            //Step 2. Verify user role
            if(rolesSet.contains(ApplicationClient.class))
            {
                isAllowed = true;
            }
        }
        return isAllowed;
    }
}
