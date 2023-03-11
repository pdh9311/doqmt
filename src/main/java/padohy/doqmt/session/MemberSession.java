package padohy.doqmt.session;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import padohy.doqmt.domain.Member;

@Data
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

  public void updateProfileImage(String filename) {
    profileImage = new StringBuilder()
        .append("/profile/")
        .append(username)
        .append("/")
        .append(filename)
        .toString();
  }
}
