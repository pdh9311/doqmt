package padohy.doqmt.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocReadDto {

  private String book;
  private String title;
  private String content;

}
