package padohy.doqmt.session;

import lombok.*;
import padohy.doqmt.domain.Member;

@Data
@ToString(exclude = "profileImage")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemberSession {

  private Long id;
  private String username;
  private String email;
  private String profileImage;

  public static MemberSession of(Member member) {
    return MemberSession.builder()
        .id(member.getId())
        .username(member.getUsername())
        .email(member.getEmail())
        .profileImage(member.getProfileImage())
        .build();
  }
  public void updateProfileImage(String dataUrl) {
    profileImage = dataUrl;
  }
}
