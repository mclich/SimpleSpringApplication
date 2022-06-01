package com.github.mclich.ssa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@SuppressWarnings("unused")
public class MvcConfig implements WebMvcConfigurer
{
    public void addViewControllers(ViewControllerRegistry registry)
    {
        //registry.addViewController("/403").setStatusCode(HttpStatus.FORBIDDEN);
        //registry.addViewController("/403").setViewName("access-denied");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new HandlerInterceptor()
        {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            {
                if(request.getRequestURI().equals("/login")&&request.getMethod().equals(HttpMethod.GET.name()))
                {
                    response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                    response.setHeader(HttpHeaders.LOCATION, "/");
                    return false;
                }
                return true;
            }
        });
    }
}