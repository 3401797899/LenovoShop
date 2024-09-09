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

    private void Set401Response(HttpServletResponse response){
        response.setStatus(401);
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write("{\"code\":1,\"message\":\"用户认证失败\"}");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        String path = request.getRequestURI();
        if (path.startsWith("/admin")) {
            try{
                String token = request.getHeader("Authorization");
                if (token == null) {
                    Set401Response(response);
                    return false;
                }
                String userId = JwtUtil.parseToken(token);
                if(!userId.equals("admin")){
                    Set401Response(response);
                    return false;
                }
                return true;
            } catch (Exception e) {
                Set401Response(response);
                return false;
            }
        }
        String token = request.getHeader("Authorization");
        if(token == null){
            Set401Response(response);
            return false;
        }
        try{
            String userId = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(userId);
            return true;
        }catch (Exception e){
            Set401Response(response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        ThreadLocalUtil.remove();
    }
}
