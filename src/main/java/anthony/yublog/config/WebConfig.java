package anthony.yublog.config;


import anthony.yublog.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") //拦截所有接口
                .excludePathPatterns("/user/login") //放行登录接口
                .excludePathPatterns("/user/register"); //放行注册接口
//                .excludePathPatterns("/user/logout"); //放行登出接口
    }
}
