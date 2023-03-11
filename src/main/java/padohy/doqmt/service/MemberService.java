package padohy.doqmt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import padohy.doqmt.constant.PathConst;
import padohy.doqmt.domain.Member;
import padohy.doqmt.dto.ChangePwReqDto;
import padohy.doqmt.dto.ProfileDto;
import padohy.doqmt.dto.SignInForm;
import padohy.doqmt.dto.SignUpForm;
import padohy.doqmt.encryption.Encryption;
import padohy.doqmt.repository.MemberRepository;
import padohy.doqmt.session.MemberSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  // username 중복확인
  public Boolean dupChkUsername(String username) {
    Optional<Member> optMember = memberRepository.findByUsername(username);
    return optMember.isPresent() ? true : false;
  }

  // email 중복확인
  public Boolean dupChkEmail(String email) {
    Optional<Member> optMember = memberRepository.findByEmail(email);
    return optMember.isPresent() ? true : false;
  }

  // 회원 가입
  public void signup(SignUpForm form) {
    Member member = Member.of(form);
    memberRepository.save(member);
  }

  // 로그인
  public MemberSession signin(SignInForm form) {
    return memberRepository.findByEmail(form.getEmail())
        .filter(m -> m.getPassword().equals(Encryption.sha512(form.getPassword())))
        .map(m -> MemberSession.of(m))
        .orElse(null);
  }

  // 회원 조회
  public Member findById(Long id) {
    return memberRepository.findById(id).get();
  }

  public ProfileDto getProfileInfo(Long id) {
    return memberRepository.findById(id)
        .map(m -> ProfileDto.of(m))
        .orElse(null);
  }

  public Boolean changePassword(Long memberId, ChangePwReqDto changePwReqDto) {
    if (!changePwReqDto.isMatch()) {
      return false;
    }
    Member member = memberRepository.findById(memberId)
        .filter(m -> m.getPassword().equals(Encryption.sha512(changePwReqDto.getCurrentPw())))
        .orElse(null);
    if (member == null) {
      return false;
    }
    member.changePassword(changePwReqDto.getNewPw());
    return true;
  }

  public String changeUsername(Long memberId, String newUsername) {
    Member member = memberRepository.findById(memberId).get();
    member.changeUsername(newUsername);
    return member.getUsername();
  }

  public File updateProfileImage(Long memberId, MultipartFile file) {
    Member member = memberRepository.findById(memberId).get();

    String originalFilename = file.getOriginalFilename();
    StringBuilder savedFilename = __createFilename(originalFilename);

    StringBuilder dirPath = __createFileDirPath(member.getUsername());
    __deleteDirectory(dirPath);
    __createDir(dirPath);
    __saveFile(file, dirPath, savedFilename.toString());

    member.updateProfileImage(savedFilename.toString());
    return new File(dirPath.toString(), savedFilename.toString());
  }

  private static void __deleteDirectory(StringBuilder dirPath) {
    try {
      FileUtils.deleteDirectory(new File(dirPath.toString()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void __createDir(StringBuilder dirPath) {
    try {
      Files.createDirectories(Paths.get(dirPath.toString()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static StringBuilder __createFilename(String originalFilename) {
    return new StringBuilder()
        .append(System.currentTimeMillis()).append("_").append(originalFilename);
  }

  private static StringBuilder __createFileDirPath(String username) {
    return new StringBuilder()
        .append(PathConst.PROJECT_PATH)
        .append(File.separator).append("src")
        .append(File.separator).append("main")
        .append(File.separator).append("resources")
        .append(File.separator).append("static")
        .append(File.separator).append("profile")
        .append(File.separator).append(username);
  }

  private static void __saveFile(MultipartFile file, StringBuilder dirPath, String filename) {
    try {
      file.transferTo(new File(dirPath.toString(), filename));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalStateException e) {
      throw new RuntimeException(e);
    }
  }
}
