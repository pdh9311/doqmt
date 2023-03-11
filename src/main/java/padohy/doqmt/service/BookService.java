package padohy.doqmt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import padohy.doqmt.domain.Book;
import padohy.doqmt.domain.Document;
import padohy.doqmt.domain.Member;
import padohy.doqmt.dto.BookAddResDto;
import padohy.doqmt.repository.BookRepository;
import padohy.doqmt.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final MemberRepository memberRepository;

  public List<Book> getBooks(String username) {
    Member member = memberRepository.findByUsername(username).get();

    return bookRepository.findByMemberAndIsDeletedOrderByIdxDesc(member, false);
  }

  public BookAddResDto saveBook(Long memberId, String bookName) {
    Member member = memberRepository.findById(memberId).get();
    Long maxIdx = bookRepository.maxIdx(member).orElse(0L);
    Book book = Book.of(bookName, maxIdx + 1, member);
    return BookAddResDto.of(bookRepository.save(book));
  }

  public int deleteToTrash(Long bookId) {
    Book book = bookRepository.findById(bookId).get();
    book.updateIsDeleted(true);
    List<Document> docs = book.getDocs();
    for (Document doc : docs) {
      doc.updateIsDeleted(true);
    }
    return book.getDocs().size();
  }

  public void updateName(Long bookId, String newName) {
    Book book = bookRepository.findById(bookId).get();
    book.updateName(newName);
  }

  public String getBookName(Long bookId) {
    return bookRepository.findById(bookId).get().getName();
  }

  public void delete(Long bookId) {
    bookRepository.deleteById(bookId);
  }

  public void restore(Long bookId) {
    Book book = bookRepository.findById(bookId).get();
    if (book.getIsDeleted() == true) {
      book.updateIsDeleted(false);
    }
  }
}
