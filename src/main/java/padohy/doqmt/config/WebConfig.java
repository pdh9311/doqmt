package padohy.doqmt.config;

import org.springframework.context.annotation.Configuration;
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
        .excludePathPatterns("/css/**", "/img/**", "/js/**", "/docs/**", "/profile/**/**")
        .excludePathPatterns("/", "/signin", "/signup/**", "/signout")
        .excludePathPatterns("/@**", "/@**/docs", "/@**/doc/read");
  }
}
