package padohy.doqmt.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/** 기준
 * 시작페이지 1
 * 최대 10개 페이지
 */
@Getter
@ToString
public class Paging {

  private int totalPage;    // 총 페이지수
  private int currPage;     // 현재 페이지 번호
  private int frontPage;    // 맨 앞에 보여줄 페이지 번호
  private int endPage;      // 맨 뒤에 보여줄 페이지 번호
  private boolean isPrev;   // 이전페이지가 존재하는지
  private boolean isNext;   // 다음페이지가 존재하는지
  private int prevPage;     // 이전페이지가 존재할 경우 이전페이지 번호
  private int nextPage;     // 다음페이지가 존재할 경우 다음페이지 번호

  @Builder
  public Paging(int total, int curr) {
    this.totalPage = total;
    this.currPage = curr;
    this.frontPage = (curr - 1) / 10 * 10 + 1;
    this.endPage = Math.max(1, Math.min((curr - 1) / 10 * 10 + 10, total));
    this.isPrev = (frontPage == 1) ? false : true;
    this.isNext = (total <= 10 || total == endPage) ? false : true;
    if (isPrev) {
      this.prevPage = frontPage - 1;
    }
    if (isNext) {
      this.nextPage = endPage + 1;
    }
  }

}
