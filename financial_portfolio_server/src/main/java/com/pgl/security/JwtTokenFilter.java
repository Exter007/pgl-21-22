package com.pgl.security;

import com.pgl.models.User;
import com.pgl.services.ContextService;
import com.pgl.services.UserDetailsServiceImpl;
import com.pgl.services.UserService;
import com.pgl.utils.ContextName;
import com.pgl.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Filter for JWT token
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    public JwtUtils jwtUtils;

    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @Autowired
    public ContextService contextService;

    protected Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        contextService.setContextName(getContext(request.getHeader("contextName"))); ;

        // Get authorization header and validate
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                userDetailsService = new UserDetailsServiceImpl();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Parse Jwt
     * @param request
     * @return
     */
    private String parseJwt(HttpServletRequest request) {

        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    /**
     * Provides the context associated with the client application requesting the services
     * @param contextName
     * @return
     */
    public ContextName getContext(String contextName){
        if (contextName.equals(ContextName.CLIENT.name())){
            return ContextName.CLIENT;
        }else if(contextName.equals( ContextName.INSTITUTION.name())){
            return ContextName.INSTITUTION;
        }
        return null;
    }


}
