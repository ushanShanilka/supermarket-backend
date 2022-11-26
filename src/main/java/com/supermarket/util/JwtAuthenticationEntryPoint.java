package com.supermarket.util;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 9/25/2022
 **/

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private Gson gson = new Gson();

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        StandardResponse response = new StandardResponse(
                Integer.parseInt(httpServletRequest.getAttribute("code").toString()),
                httpServletRequest.getAttribute("message").toString(),
                httpServletRequest.getAttribute("error"));
        String json = this.gson.toJson(response);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(json);
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }
}
