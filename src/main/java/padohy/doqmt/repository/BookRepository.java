package padohy.doqmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import padohy.doqmt.domain.Book;
import padohy.doqmt.domain.Member;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findByMember(Member member);

  List<Book> findByMemberAndIsDeleted(Member member, Boolean isDeleted);

  List<Book> findByMemberAndIsDeletedOrderByIdxDesc(Member member, Boolean isDeleted);

  @Query(value = "SELECT max(b.idx) FROM Book b WHERE b.member = :member")
  Optional<Long> maxIdx(@Param("member") Member member);

}
