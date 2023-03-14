package padohy.doqmt.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.dto.ChangePwReqDto;
import padohy.doqmt.dto.ProfileDto;
import padohy.doqmt.service.MemberService;
import padohy.doqmt.session.MemberSession;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/@{username}")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/profile/setting")
  public String profileSettingForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      Model model) {

    ProfileDto profileDto = memberService.getProfileInfo(memberSession.getId());
    model.addAttribute("member", profileDto);
    return "profile-setting";
  }

  @PatchMapping("/password/{id}")
  @ResponseBody
  public Boolean changePassword(@PathVariable("id") Long memberId,
                                @RequestBody ChangePwReqDto changePwReqDto,
                                HttpSession session) {
    Boolean result = memberService.changePassword(memberId, changePwReqDto);
    if (result == true) {
      session.invalidate();
      return true;
    }
    return false;
  }

  @PostMapping("/check/username")
  @ResponseBody
  public Boolean checkUsername(@RequestBody Map<String, String> usernameMap) {
    return memberService.dupChkUsername(usernameMap.get("username"));
  }

  @PatchMapping("/username/{id}")
  @ResponseBody
  public String changeUsername(@PathVariable("id") Long memberId,
                               @RequestBody Map<String, String> usernameMap) {
    return memberService.changeUsername(memberId, usernameMap.get("username"));
  }

  @PostMapping("/profile-image/{id}")
  @ResponseBody
  public String updateProfileImage(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable("id") Long memberId,
      @RequestBody MultipartFile file,
      HttpSession session) {
    
    String imageDataUrl = memberService.updateProfileImage(memberId, file);
    memberSession.updateProfileImage(imageDataUrl);

    session.setAttribute(SessionConst.MEMBER_SESSION, memberSession);
    log.info("member = {}", memberSession);

    return memberSession.getProfileImage();
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public String deleteMember(@PathVariable("id") Long memberId,
                             HttpSession session) {
    memberService.delete(memberId);
    session.invalidate();
    return "bye";
  }


}
