package padohy.doqmt.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Book extends BaseTime {

  @Id @GeneratedValue
  @Column(name = "book_id")
  private Long id;

  private String name;

  private Long idx;

  private Boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "book")
  private List<Document> docs;

  public static Book of(String name, long idx, Member member) {
    return Book.builder()
        .name(name)
        .idx(idx)
        .isDeleted(false)
        .member(member)
        .build();
  }

  public void updateIsDeleted(Boolean newIsDel) {
    isDeleted = newIsDel;
  }

  public void updateName(String newName) {
    name = newName;
  }

}
