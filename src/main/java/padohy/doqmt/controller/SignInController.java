package padohy.doqmt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.dto.SignInForm;
import padohy.doqmt.service.MemberService;
import padohy.doqmt.session.MemberSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignInController {

  private final MemberService memberService;

  @GetMapping("/signin")
  public String signinForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @ModelAttribute("signin") SignInForm signin) {
    if (memberSession != null) {
      return "redirect:/@" + memberSession.getUsername();
    }
    return "signin";
  }

  @PostMapping("/signin")
  public String signin(@ModelAttribute("signin") SignInForm form, BindingResult bindingResult,
                       @RequestParam(defaultValue = "/") String redirectURI,
                       HttpSession session) {
    MemberSession memberSession = memberService.signin(form);
    log.info("MemberSession={}", memberSession);
    if (memberSession == null) {
      bindingResult.reject("loginFail", null);
      return "signin";
    }

    session.setAttribute(SessionConst.MEMBER_SESSION, memberSession);
    if (redirectURI.equals("/")) {
      redirectURI = "/@" + memberSession.getUsername();
    }
    return "redirect:" + redirectURI;
  }

  @GetMapping("/signout")
  public String signout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }

}
