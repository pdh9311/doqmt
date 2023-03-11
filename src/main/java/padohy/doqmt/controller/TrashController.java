package padohy.doqmt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import padohy.doqmt.constant.SessionConst;
import padohy.doqmt.dto.TrashDto;
import padohy.doqmt.service.TrashService;
import padohy.doqmt.session.MemberSession;
import padohy.doqmt.utils.Paging;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/@{username}")
@RequiredArgsConstructor
public class TrashController {

  private final TrashService trashService;

  @GetMapping("/trash")
  public String trashForm(
      @SessionAttribute(name = SessionConst.MEMBER_SESSION, required = false) MemberSession memberSession,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "18") int size,
      Model model) {
    int offset = (page - 1) * size;
    List<TrashDto> deletedDocs = trashService.deletedList(memberSession.getId(), offset, size);
    Integer deletedTotalCount = trashService.deletedTotalCount(memberSession.getId(), offset, size);
    Paging pg = new Paging(deletedTotalCount / size, page);

    log.info("member = {}", memberSession);

    model.addAttribute("member", memberSession);
    model.addAttribute("docs", deletedDocs);
    model.addAttribute("pg", pg);
    return "trash";
  }

}
