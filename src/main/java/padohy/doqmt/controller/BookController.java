package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.domain.Book;
import padohy.doqmt.dto.BookAddResDto;
import padohy.doqmt.service.BookService;
import padohy.doqmt.session.MemberSession;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/@{username}")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping
  public String bookForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable String username,
      Model model) {
    log.info("memberSession={}, username={}", memberSession, username);

    boolean isMySelf = true;
    if (memberSession == null || !username.equals(memberSession.getUsername())) {
      isMySelf = false;
    }

    // 사용자 카테고리 리스트 불러오기
    List<Book> books = bookService.getBooks(username);

    model.addAttribute("member", memberSession);
    model.addAttribute("username", username);
    model.addAttribute("isMySelf", isMySelf);
    model.addAttribute("books", books);
    return "book";
  }

  @PostMapping("/book/add")
  @ResponseBody
  public BookAddResDto addBook(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @RequestBody Map<String, String> bookMap) {
    log.info("memberId={}", memberSession.getId());
    return bookService.saveBook(memberSession.getId(), bookMap.get("bookName"));
  }

  @PatchMapping("/book/delete/{id}")
  @ResponseBody
  public Integer deleteToTrash(@PathVariable("id") Long bookId) {
    int docsCount = bookService.deleteToTrash(bookId);
    return docsCount;
  }

  @PatchMapping("/book/restore/{id}")
  @ResponseBody
  public String restore(@PathVariable("id") Long bookId) {
    bookService.restore(bookId);
    return "book restored";
  }

  @DeleteMapping("/book/{id}")
  @ResponseBody
  public String delete(@PathVariable("id") Long bookId) {
    bookService.delete(bookId);
    return "deleted";
  }

  @PatchMapping("/book/name/{id}")
  @ResponseBody
  public String updateName(@PathVariable("id") Long BookId,
                           @RequestBody Map<String, String> nameMap) {
    bookService.updateName(BookId, nameMap.get("name"));
    return "updated";
  }

}





















