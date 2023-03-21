package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import padohy.doqmt.service.EmailSenderService;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmailController {

  private final EmailSenderService emailSenderService;

  @PostMapping("/auth-email")
  @ResponseBody
  public String authEmail(@RequestBody Map<String, String> emailMap) {
    log.info("Send Email = {}", emailMap.get("email"));
    return emailSenderService.authEmail(emailMap.get("email"));
  }

}
