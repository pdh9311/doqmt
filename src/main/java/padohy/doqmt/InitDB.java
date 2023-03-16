package padohy.doqmt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import padohy.doqmt.repository.BookRepository;
import padohy.doqmt.repository.DocumentRepository;
import padohy.doqmt.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class InitDB {

  private final MemberRepository memberRepository;
  private final BookRepository bookRepository;
  private final DocumentRepository documentRepository;

  @PostConstruct
  public void initDB() {

  }

}
/*Member member = Member.builder()
        .username("padohy")
        .email("padohy@gmail.com")
        .password(Encryption.sha512("qwe123!@#"))
        .build();
    memberRepository.save(member);*/