package padohy.doqmt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import padohy.doqmt.domain.Member;
import padohy.doqmt.encryption.Encryption;
import padohy.doqmt.repository.BookRepository;
import padohy.doqmt.repository.DocumentRepository;
import padohy.doqmt.repository.MemberRepository;

//@Component
@RequiredArgsConstructor
public class InitDB {

  private final MemberRepository memberRepository;
  private final BookRepository bookRepository;
  private final DocumentRepository documentRepository;

  @PostConstruct
  public void initDB() {
  }

}
