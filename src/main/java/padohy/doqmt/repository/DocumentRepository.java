package padohy.doqmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import padohy.doqmt.domain.Book;
import padohy.doqmt.domain.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

  List<Document> findByBook(Book book);

  List<Document> findByBookAndIsDeletedOrderByIdxDesc(Book book, Boolean isDeleted);

  @Query(value = "SELECT max(d.idx) FROM Document d WHERE d.book = :book")
  Optional<Long> maxIdx(@Param("book") Book book);

  @Query(value = "SELECT d FROM Member m" +
      " JOIN Book b ON b.member = m" +
      " JOIN Document d ON d.book = b" +
      " WHERE m.id = :memberId AND d.isDeleted = :isDeleted" +
      " ORDER BY d.updatedTime DESC")
  List<Document> findIsDeletedDocuments(@Param("memberId") Long id, @Param("isDeleted") Boolean isDeleted);

}
