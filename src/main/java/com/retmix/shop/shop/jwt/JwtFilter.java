package com.retmix.shop.shop.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private final JWTProvider provider;

    public JwtFilter(JWTProvider provider) {
        this.provider = provider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = provider.resolveToken((HttpServletRequest) request);
        try {
            if (token!=null && provider.validationToken(token)){
                Authentication authentication = provider.getAuth(token);
                if (authentication!=null){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) response).sendError(e.getHttpStatus().value());
            try {
                throw new JwtAuthException("Jwt token is exp or invalid");
            } catch (JwtAuthException ex) {
                throw new RuntimeException(ex);
            }
        }

        chain.doFilter(request, response);
    }
}
