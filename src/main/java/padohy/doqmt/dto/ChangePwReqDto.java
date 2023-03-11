package padohy.doqmt.dto;

import lombok.Data;

@Data
public class ChangePwReqDto {
  private String currentPw;
  private String newPw;
  private String againPw;

  public Boolean isMatch() {
    return newPw.equals(againPw) ? true : false;
  }
}
