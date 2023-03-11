package padohy.doqmt.dto;

import lombok.Builder;
import lombok.Data;
import padohy.doqmt.domain.Book;

@Data
@Builder
public class BookAddResDto {

  private Long bookId;
  private String bookName;
  private Long idx;

  public static BookAddResDto of(Book book) {
    return BookAddResDto.builder()
        .bookId(book.getId())
        .bookName(book.getName())
        .idx(book.getIdx())
        .build();
  }

}
