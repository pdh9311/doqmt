package padohy.doqmt.dto;

import lombok.Data;

@Data
public class MsgDto {
  private String message;

  public MsgDto(String message) {
    this.message = message;
  }
}
