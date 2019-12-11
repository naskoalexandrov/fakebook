package com.example.fakebukproject.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class IconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView){

        String icon = "https://i.ibb.co/8rNq13X/fa.jpg";

        if(modelAndView != null){
            modelAndView.addObject("icon", icon);
        }
    }
}
