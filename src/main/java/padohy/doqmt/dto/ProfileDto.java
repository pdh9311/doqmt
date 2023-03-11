package padohy.doqmt.dto;

import lombok.Builder;
import lombok.Data;
import padohy.doqmt.domain.Member;

import java.time.LocalDateTime;

@Data
@Builder
public class ProfileDto {

  private Long id;
  private String profileImage;
  private String username;
  private String email;
  private LocalDateTime createdTime;

  public static ProfileDto of(Member member) {
    return ProfileDto.builder()
        .id(member.getId())
        .profileImage(member.getProfileImage())
        .username(member.getUsername())
        .email(member.getEmail())
        .createdTime(member.getCreatedTime())
        .build();
  }

}
