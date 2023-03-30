package padohy.doqmt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
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

  public Boolean changePassword(String email, String newPassword) {
    Optional<Member> optMember = memberRepository.findByEmail(email);
    if (!optMember.isPresent()) {
      return false;
    }
    Member member = optMember.get();
    member.changePassword(newPassword);
    return true;
  }

  public String changeUsername(Long memberId, String newUsername) {
    Member member = memberRepository.findById(memberId).get();
    changeDirName(member.getUsername(), newUsername);
    member.changeUsername(newUsername);
    return member.getUsername();
  }

  private static void changeDirName(String originUsername, String newUsername) {
    try {
      File origin = FileUtils.getFile(__fileDirPath(originUsername).toString());
      File after = FileUtils.getFile(__fileDirPath(newUsername).toString());

      if (origin.exists()) {
        FileUtils.moveDirectory(origin, after);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String updateProfileImage(Long memberId, MultipartFile file) {
    Member member = memberRepository.findById(memberId).get();

    try {
      byte[] fileBytes = file.getInputStream().readAllBytes();
      String base64String = new String(Base64.encodeBase64String(fileBytes));
      String imageDataUrl = new StringBuilder()
          .append("data:")
          .append(file.getContentType())
          .append(";base64,")
          .append(base64String)
          .toString();
      member.updateProfileImage(imageDataUrl);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return member.getProfileImage();
  }

  public void delete(Long memberId) {
    memberRepository.deleteById(memberId);
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

}
