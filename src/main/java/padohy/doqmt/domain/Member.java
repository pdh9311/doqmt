package padohy.doqmt.domain;

import jakarta.persistence.*;
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

  @Lob
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

  public void updateProfileImage(String imageDataUrl) {
    profileImage = imageDataUrl;
  }
}
