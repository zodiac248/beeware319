 package com.panpal;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import org.springframework.security.access.AccessDeniedException;
 import org.springframework.stereotype.Component;

 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;

 @Component
 public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
     @Override
     public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

         ObjectMapper objectMapper = new ObjectMapper();
         ResultVO<Object> result = new ResultVO<>();
         result.setCode(50);
         result.setMsg("invalid request without token!");
         response.getWriter().write(objectMapper.writeValueAsString(result));
     }
 }