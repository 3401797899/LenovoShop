package com.sepractice.lenovoshop.interceptors;

import com.sepractice.lenovoshop.utils.JwtUtil;
import com.sepractice.lenovoshop.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception{
        String token = request.getHeader("Authorization");
        if(token.length()  == 0){
            response.setStatus(401);
            return false;
        }
        try{
            String userId = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(userId);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        ThreadLocalUtil.remove();
    }
}
