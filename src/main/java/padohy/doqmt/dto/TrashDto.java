package padohy.doqmt.dto;

import lombok.Builder;
import lombok.Data;
import padohy.doqmt.domain.Document;

import java.time.LocalDateTime;

@Data
@Builder
public class TrashDto {

  private Long bookId;
  private Long docId;
  private String bookName;
  private String docTitle;
  private LocalDateTime deletedTime;

  public static TrashDto of(Document document) {
    return TrashDto.builder()
        .bookId(document.getBook().getId())
        .docId(document.getId())
        .bookName(document.getBook().getName())
        .docTitle(document.getTitle())
        .deletedTime(document.getUpdatedTime())
        .build();
  }

}
