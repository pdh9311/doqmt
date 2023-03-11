package padohy.doqmt.dto;

import lombok.Data;

@Data
public class DocSaveReqDto {
  private Long book;
  private String title;
  private String content;
}
