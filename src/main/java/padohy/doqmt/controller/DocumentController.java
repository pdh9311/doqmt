package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.domain.Document;
import padohy.doqmt.dto.DocReadDto;
import padohy.doqmt.dto.DocSaveReqDto;
import padohy.doqmt.dto.EditReqDto;
import padohy.doqmt.service.BookService;
import padohy.doqmt.service.DocumentService;
import padohy.doqmt.session.MemberSession;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/@{username}")
@RequiredArgsConstructor
public class DocumentController {

  private final DocumentService documentService;
  private final BookService bookService;



  @GetMapping("/docs")
  public String documentForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable String username,
      @RequestParam("book") Long bookId,
      Model model) {

    boolean isMySelf = true;
    if (memberSession == null || !username.equals(memberSession.getUsername())) {
      isMySelf = false;
    }

    String bookName = bookService.getBookName(bookId);

    List<Document> documents = documentService.getDocuments(username, bookId);

    model.addAttribute("member", memberSession);
    model.addAttribute("username", username);
    model.addAttribute("bookName", bookName);
    model.addAttribute("bookId", bookId);
    model.addAttribute("isMySelf", isMySelf);
    model.addAttribute("documents", documents);
    return "document";
  }

  @GetMapping("/doc/write")
  public String writeForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable("username") String username,
      @RequestParam("book") Long bookId,
      Model model) {

    if (memberSession == null || !username.equals(memberSession.getUsername())) {
      return "redirect:/";
    }

    String bookName = bookService.getBookName(bookId);

    model.addAttribute("member", memberSession);
    model.addAttribute("username", username);
    model.addAttribute("bookName", bookName);
    model.addAttribute("bookId", bookId);
    return "write";
  }

  @PostMapping("/doc/write")
  @ResponseBody
  public Long saveDocument(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @RequestBody DocSaveReqDto docSaveReqDto,
      @PathVariable("username") String username) {
    Long documentId = -1L;
    if (memberSession != null && username.equals(memberSession.getUsername())) {
      documentId = documentService.write(docSaveReqDto, username);
    }
    return documentId;
  }

  @GetMapping("/doc/read")
  public String readForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable("username") String username,
      @RequestParam("book") Long bookId,
      @RequestParam("doc") Long docId,
      Model model) {

    boolean isMySelf = true;
    if (memberSession == null || !username.equals(memberSession.getUsername())) {
      isMySelf = false;
    }

    DocReadDto docReadDto = documentService.read(docId, username);

    model.addAttribute("member", memberSession);
    model.addAttribute("username", username);
    model.addAttribute("bookId", bookId);
    model.addAttribute("docId", docId);
    model.addAttribute("isMySelf", isMySelf);
    model.addAttribute("docReadDto", docReadDto);

    return "read";
  }

  @GetMapping("/doc/edit")
  public String editForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable("username") String username,
      @RequestParam("book") Long bookId,
      @RequestParam("doc") Long docId,
      Model model) {

    if (memberSession == null || !username.equals(memberSession.getUsername())) {
      return "redirect:/";
    }

    DocReadDto docReadDto = documentService.read(docId, username);
    String bookName = bookService.getBookName(bookId);

    model.addAttribute("member", memberSession);
    model.addAttribute("username", username);
    model.addAttribute("bookId", bookId);
    model.addAttribute("bookName", bookName);
    model.addAttribute("docId", docId);
    model.addAttribute("docReadDto", docReadDto);
    return "edit";
  }

  @PatchMapping("/doc/title/{id}")
  @ResponseBody
  public String updateTitle(@PathVariable("username") String username,
                            @PathVariable("id") Long docId,
                            @RequestBody Map<String, String> newTitleMap) {
    documentService.updateTitle(docId, username, newTitleMap.get("title"));
    return "updated";
  }

  @PatchMapping("/doc/delete/{id}")
  @ResponseBody
  public String deleteToTrash(@PathVariable("id") Long docId) {
    documentService.deleteToTrash(docId);
    return "deleted to trash";
  }

  @PatchMapping("/doc/restore/{id}")
  @ResponseBody
  public String restore(@PathVariable("id") Long docId) {
    documentService.restore(docId);
    return "document restored";
  }

  @DeleteMapping("/doc/{id}")
  @ResponseBody
  public String delete(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable("id") Long docId) {
    documentService.delete(docId, memberSession.getUsername());
    return "deleted";
  }

  @PutMapping("/doc/edit")
  @ResponseBody
  public String edit(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @PathVariable("username") String username,
      @RequestBody EditReqDto editReqDto) {

    if (memberSession == null || !username.equals(memberSession.getUsername())) {
      return "redirect:/";
    }

    documentService.edit(username, editReqDto.getDocId(), editReqDto.getTitle(), editReqDto.getContent());
    return "edited";
  }
}

