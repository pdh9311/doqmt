package padohy.doqmt.dto;

import lombok.Data;

@Data
public class EditReqDto {

  private Long docId;
  private String title;
  private String content;

}
