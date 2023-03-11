package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.session.MemberSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

  @GetMapping("/")
  public String home(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      Model model) {
    model.addAttribute("member", memberSession);
    return "home";
  }

}
