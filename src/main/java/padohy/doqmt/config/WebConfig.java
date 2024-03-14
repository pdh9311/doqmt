package padohy.doqmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import padohy.doqmt.interceptor.SignInInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new SignInInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/error", "/*.ico", "/css/**", "/img/**", "/js/**", "/docs/**", "/profile/**")
        .excludePathPatterns("/", "/signin", "/signup/**", "/signout", "/reset-password", "/auth-email")
        .excludePathPatterns("/@*", "/@*/docs", "/@*/doc/read");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://www.doqmt.com", "http://doqmt.com")
            .allowedMethods("*");
  }
}
