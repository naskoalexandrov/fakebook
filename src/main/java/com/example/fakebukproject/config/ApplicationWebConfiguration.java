package com.example.fakebukproject.config;

import com.example.fakebukproject.web.interceptors.IconInterceptor;
import com.example.fakebukproject.web.interceptors.TitleIntercreptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebConfiguration implements WebMvcConfigurer {

    private final TitleIntercreptor interceptor;
    private final IconInterceptor iconInterceptor;

    @Autowired
    public ApplicationWebConfiguration(TitleIntercreptor intercreptor, IconInterceptor iconInterceptor) {
        this.interceptor = intercreptor;
        this.iconInterceptor = iconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.interceptor);
        registry.addInterceptor(this.iconInterceptor);
    }
}
