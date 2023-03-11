package padohy.doqmt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

  private final JavaMailSender mailSender;

  public String authEmail(String to) {
    UUID uuid = UUID.randomUUID();

    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom("padohy@gmail.com");
    simpleMailMessage.setTo(to);
    simpleMailMessage.setSubject("DOQMT 인증 코드 확인해주세요.");
    simpleMailMessage.setText("" + uuid);

    mailSender.send(simpleMailMessage);
    return uuid.toString();
  }

}
