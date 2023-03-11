package padohy.doqmt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import padohy.doqmt.constant.PathConst;
import padohy.doqmt.domain.Book;
import padohy.doqmt.domain.Document;
import padohy.doqmt.domain.Member;
import padohy.doqmt.dto.DocSaveReqDto;
import padohy.doqmt.dto.DocReadDto;
import padohy.doqmt.repository.BookRepository;
import padohy.doqmt.repository.DocumentRepository;
import padohy.doqmt.repository.MemberRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {

  private final DocumentRepository documentRepository;
  private final BookRepository bookRepository;
  private final MemberRepository memberRepository;

  public List<Document> getDocuments(String username, Long bookId) {
    Member member = memberRepository.findByUsername(username).get();
    Book book = bookRepository.findById(bookId).get();
    if (book.getMember().getId() != member.getId()) {
      throw new RuntimeException(); // 자신의 문서가 아닌 경우
    }
    return documentRepository.findByBookAndIsDeletedOrderByIdxDesc(book, false);
  }

  public Long write(DocSaveReqDto docSaveReqDto, String username) {
    String title = docSaveReqDto.getTitle();
    String content = docSaveReqDto.getContent();

    Book book = bookRepository.findById(docSaveReqDto.getBook()).get();
    Long maxIdx = documentRepository.maxIdx(book).orElse(0L);

    // 파일 저장(디렉토리 생성 + 파일 생성 + 파일 데이터 입력)
    String fileName = __saveFile(username, title, content);

    Document document = Document.of(title, fileName, maxIdx + 1, book);
    Document savedDocument = documentRepository.save(document);
    return savedDocument.getId();
  }


  public DocReadDto read(Long docId, String username) {
    Document document = documentRepository.findById(docId).get();
    String filename = document.getFilename();

    int startIdx = filename.indexOf('_');
    int endIdx = filename.lastIndexOf(".md");
    String title = filename.substring(startIdx + 1, endIdx);

    // 파일 경로
    StringBuilder filePath = __filePath(username, filename);

    // 파일 읽기
    StringBuilder content = __readFile(filePath);

    return DocReadDto.builder()
        .book(document.getBook().getName())
        .title(title)
        .content(content.toString())
        .build();
  }

  public void updateTitle(Long docId, String username, String newTitle) {
    Document document = documentRepository.findById(docId).get();
    String originFilename = document.getFilename();
    try {

      // 기존 파일 경로
      StringBuilder originFilePath = __filePath(username, originFilename);

      // 새로운 파일명
      StringBuilder newFilename = __originNum_NewFilename(newTitle, originFilename);

      // 새로운 파일 경로
      StringBuilder newFilePath = __filePath(username, newFilename.toString());

      // 파일명 변경
      Files.move(Paths.get(originFilePath.toString()), Paths.get(newFilePath.toString()));

      document.updateTitleAndFilename(newTitle, newFilename.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteToTrash(Long docId) {
    Document document = documentRepository.findById(docId).get();
    document.updateIsDeleted(true);
  }

  public void restore(Long docId) {
    Document document = documentRepository.findById(docId).get();
    document.updateIsDeleted(false);
  }

  public void delete(Long docId, String username) {
    Document document = documentRepository.findById(docId).get();
    Book book = document.getBook();

    __deleteFile(username, document.getFilename());
    documentRepository.deleteById(docId);

    if (book.getIsDeleted() == true &&
      book.getDocs().size() == 1) {
      bookRepository.deleteById(book.getId());
    }
  }

  /**
   * Files.deleteIfExists() 를 사용하면,
   * 파일이 존재하는 경우에는 파일을 삭제하고,
   * 파일이 존재하지 않는 경우에는 파일을 삭제하지 않고, false를 리턴합니다.
   * (Files.delete() 의 경우에는, 파일이 존재하지 않는 경우 NoSuchFileException이 발생하며,
   * deleteIfExist()의 경우에는 Exception이 발생하지 않습니다.)
   */
  public void edit(String username, Long docId, String newTitle, String content) {
      Document document = documentRepository.findById(docId).get();

      // 기존 파일 삭제
      __deleteFile(username, document.getFilename());

      // 새로운 파일로 content 저장
      String newFilename = __saveFile(username, newTitle, content);

      document.updateTitleAndFilename(newTitle, newFilename);
  }

  private static StringBuilder __fileDirPath(String username) {
    return new StringBuilder()
        .append(PathConst.PROJECT_PATH)
        .append(File.separator).append("src")
        .append(File.separator).append("main")
        .append(File.separator).append("resources")
        .append(File.separator).append("static")
        .append(File.separator).append("docs")
        .append(File.separator).append(username);
  }

  private static StringBuilder __filePath(String username, String filename) {
    return new StringBuilder()
        .append(PathConst.PROJECT_PATH)
        .append(File.separator).append("src")
        .append(File.separator).append("main")
        .append(File.separator).append("resources")
        .append(File.separator).append("static")
        .append(File.separator).append("docs")
        .append(File.separator).append(username)
        .append(File.separator).append(filename);
  }

  /**
   * Files.createDirectories()
   * 디렉토리가 존재하더라도, exception이 발생하지 않고,
   * 부모 디렉토리가 존재하지 않으면, 자동으로 부모 디렉토리를 만들어 줍니다.
   *
   * @return filename
   */
  private static String __saveFile(String username, String title, String content) {
    StringBuilder fileDirPath;
    StringBuilder filename = new StringBuilder();

    try {
      // 파일 저장 디렉토리 생성
      fileDirPath = __fileDirPath(username);
      Files.createDirectories(Paths.get(fileDirPath.toString()));

      // 파일 생성
      filename.append(System.currentTimeMillis())
          .append("_")
          .append(title)
          .append(".md");
      File file = new File(fileDirPath.toString(), filename.toString());
      if (!file.exists()) {
        file.createNewFile();
      }
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(content);
      bw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return filename.toString();
  }

  private static StringBuilder __readFile(StringBuilder filePath) {
    StringBuilder content = new StringBuilder();
    try {
      File file = new File(filePath.toString());

      BufferedReader br = new BufferedReader(new FileReader(file));
      String str;
      while ((str = br.readLine()) != null) {
        content.append(str).append('\n');
      }
      br.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return content;
  }

  private static StringBuilder __originNum_NewFilename(String newTitle, String originFilename) {
    StringBuilder newFilename = new StringBuilder();
    String originNumber = originFilename.substring(0, originFilename.indexOf("_"));
    newFilename.append(originNumber).append("_").append(newTitle).append(".md");
    return newFilename;
  }

  private static void __deleteFile(String username, String filename) {
    try {
      StringBuilder filePath = __filePath(username, filename);
      Files.deleteIfExists(Paths.get(filePath.toString()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
