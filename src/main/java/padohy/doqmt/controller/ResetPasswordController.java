package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.dto.ForgotPwDto;
import padohy.doqmt.service.MemberService;
import padohy.doqmt.session.MemberSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ResetPasswordController {

  private final MemberService memberService;

  @GetMapping("/reset-password")
  public String resetPasswordForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession) {
    if (memberSession != null) {
      return "redirect:/@" + memberSession.getUsername();
    }
    return "reset-password";
  }

  @PatchMapping("/reset-password")
  @ResponseBody
  public String resetPassword(@RequestBody ForgotPwDto forgotPwDto) {
    Boolean result = memberService.changePassword(forgotPwDto.getEmail(), forgotPwDto.getNewPassword());
    return result == true ? "ok" : "fail";
  }

}
