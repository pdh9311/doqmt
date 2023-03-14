package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import padohy.doqmt.dto.SignUpForm;
import padohy.doqmt.service.EmailSenderService;
import padohy.doqmt.service.MemberService;

import jakarta.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

  private final MemberService memberService;
  private final EmailSenderService emailSenderService;

  @GetMapping
  public String signupForm(@ModelAttribute("signUp") SignUpForm signUp) {
    return "signup";
  }

  @PostMapping
  public String signup(@Valid @ModelAttribute("signup") SignUpForm form, BindingResult bindingResult) {
    log.info("form={}", form);

    // 유효성 검사에서 에러 발생한 경우
    if (bindingResult.hasErrors()) {
      log.error("error ~~~~~~~~~~~~~~~~~");
      return "signup";
    }

    memberService.signup(form);
    return "redirect:/signin";
  }

  @PostMapping("/check-username")
  @ResponseBody
  public Boolean checkUsername(@RequestBody Map<String, String> usernameMap) {
    return memberService.dupChkUsername(usernameMap.get("username"));
  }

  @PostMapping("/check-email")
  @ResponseBody
  public Boolean checkEmail(@RequestBody Map<String, String> emailMap) {
    return memberService.dupChkEmail(emailMap.get("email"));
  }

}
