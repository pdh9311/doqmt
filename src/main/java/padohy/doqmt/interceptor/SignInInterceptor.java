package padohy.doqmt.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import padohy.doqmt.constant.SessionConst;

@Slf4j
public class SignInInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession(false);
    log.info("redirectURI={}", request.getRequestURI());
    if (session == null || session.getAttribute(SessionConst.MEMBER_SESSION) == null) {
      response.sendRedirect(("/signin?redirectURI=" + request.getRequestURI()));
      return false;
    }
    return true;
  }


}
