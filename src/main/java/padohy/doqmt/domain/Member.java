package padohy.doqmt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import padohy.doqmt.dto.SignUpForm;
import padohy.doqmt.encryption.Encryption;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BaseTime {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String username;

  private String email;

  private String password;

  private String profileImage;

  public static Member of(SignUpForm form) {
    return Member.builder()
        .username(form.getUsername())
        .email(form.getEmail())
        .password(Encryption.sha512(form.getPassword()))
        .build();
  }

  public void changePassword(String newPw) {
    password = Encryption.sha512(newPw);
  }

  public void changeUsername(String newUsername) {
    username = newUsername;
  }

  public void updateProfileImage(String savedFilename) {
    profileImage = new StringBuilder()
        .append("/profile/")
        .append(username)
        .append("/")
        .append(savedFilename)
        .toString();
  }
}
