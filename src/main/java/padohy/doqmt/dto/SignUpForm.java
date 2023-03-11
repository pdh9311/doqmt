package padohy.doqmt.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class SignUpForm {

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9_-]{4,30}$")
  private String username;

  @NotBlank
  @Email(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
  private String email;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9`~!@#$%^&*()\\[\\]\\\\\\|{};':\",./<>?_+=-]{8,}$")
  private String password;
}
