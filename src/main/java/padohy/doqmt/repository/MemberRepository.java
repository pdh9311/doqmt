package padohy.doqmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import padohy.doqmt.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByUsername(String username);
  Optional<Member> findByEmail(String email);
}
