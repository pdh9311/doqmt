package padohy.doqmt.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Document extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "document_id")
  private Long id;

  private String title;

  private String filename;

  private Long hits;

  private Boolean isDeleted;

  private Long idx;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  public static Document of(String title, String filename, Long idx, Book book) {
    return Document.builder()
        .title(title)
        .filename(filename)
        .hits(0L)
        .isDeleted(false)
        .idx(idx)
        .book(book)
        .build();
  }

  public void updateTitleAndFilename(String newTitle, String newFilename) {
    title = newTitle;
    filename = newFilename;
  }

  public void updateIsDeleted(Boolean newIsDel) {
    isDeleted = newIsDel;
  }
}
