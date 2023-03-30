package padohy.doqmt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import padohy.doqmt.dto.TrashDto;
import padohy.doqmt.repository.BookRepository;
import padohy.doqmt.repository.DocumentRepository;
import padohy.doqmt.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TrashService {

  private final MemberRepository memberRepository;
  private final BookRepository bookRepository;
  private final DocumentRepository documentRepository;

  public List<TrashDto> deletedList(Long memberId, int offset, int limit) {
    return documentRepository.findIsDeletedDocuments(memberId, true).stream()
        .skip(offset)
        .limit(limit)
        .map(d -> TrashDto.of(d))
        .toList();
  }

  public Integer deletedTotalCount(Long memberId) {
    return documentRepository.findIsDeletedDocuments(memberId, true).size();
  }
}
