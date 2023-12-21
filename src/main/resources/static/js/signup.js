const $usernameBox = document.querySelector(".username-box");
const $usernameInput = document.querySelector("#username");
const $usernameLabel = document.querySelector(".username-box > label");
const $usernameStatus = document.querySelector(".username-status");

const $emailBox = document.querySelector(".email-box");
const $emailInput = document.querySelector("#email");
const $emailLabel = document.querySelector(".email-box > label");
const $emailSendBtn = document.querySelector(".email-box > button");
const $emailStatus = document.querySelector(".email-status");

const $emailCheckBox = document.querySelector(".email-check-box");
const $emailCheckInput = document.querySelector(".email-check-box > input");
const $emailCheckBtn = document.querySelector(".email-check-box > button");
const $emailCheckStatus = document.querySelector(".email-check-status");

const $passwordBox = document.querySelector(".password-box");
const $passwordInput = document.querySelector("#password");
const $passwordLabel = document.querySelector(".password-box > label");
const $passwordCheckBtn = document.querySelector(".password-box > button");
const $passwordCheckImage = document.querySelector(
  ".password-box > button > img"
);
const $passwordStatus = document.querySelector(".password-status");

let isBlindPw = true;

const $signupBtn = document.querySelector(".signup-btn");

const $signupForm = document.querySelector("form");

const signUpCheckList = {
  username: false,
  email: false,
  check: "",
  emailCheck: false,
  password: false,
};

const usernameRegExp = /^[a-zA-Z0-9_-]{4,30}$/;
const emailRegExp =
  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
const passwordRegExp = /^[a-zA-Z0-9`~!@#$%^&*()\[\]\\\|{};':",./<>?_+=-]{8,}$/;

$signupBtn.addEventListener("click", (e) => {
  const {username, email, emailCheck, password} = signUpCheckList;
  if (username === true && email === true && emailCheck === true && password === true) {
    $signupForm.submit();
  } else {
    e.preventDefault();
  }
});

$usernameInput.addEventListener("focusin", () => {
  if (!$usernameLabel.classList.contains("label-move")) {
    $usernameLabel.classList.add("label-move");
    $usernameBox.classList.add("border-black");
  }
});
$usernameInput.addEventListener("input", () => {
  if (!$usernameLabel.classList.contains("label-move")) {
    $usernameLabel.classList.add("label-move");
    $usernameBox.classList.add("border-black");
  }
});

$usernameInput.addEventListener("focusout", () => {
  if ($usernameInput.value === "") {
    $usernameLabel.classList.remove("label-move");
    $usernameBox.classList.remove("border-black");
  }
  axios
    .post("https://doqmt.gonetis.com/signup/check-username", {
      username: $usernameInput.value,
    })
    .then((response) => {
      const data = response.data;
      if (data === true) {
        // 중복있음
        $usernameLabel.classList.remove("font-success");
        $usernameBox.classList.remove("border-success");
        $usernameLabel.classList.add("font-fail");
        $usernameBox.classList.add("border-fail");
        $usernameInput.classList.add("font-fail");
        $usernameStatus.textContent = "Username is already in use";
        signUpCheckList.username = false;
      } else if (!usernameRegExp.test($usernameInput.value)) {
        // 정규표현식이 일치하지 않는 경우
        $usernameLabel.classList.remove("font-success");
        $usernameBox.classList.remove("border-success");
        $usernameLabel.classList.add("font-fail");
        $usernameBox.classList.add("border-fail");
        $usernameInput.classList.add("font-fail");
        $usernameStatus.textContent =
          "Use 4 to 30 letters & digits & underline & hypen only.";
        signUpCheckList.username = false;
      } else {
        // 중복없음 + 정규표현식과 일치함
        $usernameStatus.textContent = "";
        $usernameInput.classList.remove("font-fail");
        $usernameLabel.classList.add("font-success");
        $usernameBox.classList.add("border-success");
        signUpCheckList.username = true;
      }
    })
    .catch((error) => {
      console.error(error);
    });
});

$emailInput.addEventListener("focusin", () => {
  if (!$emailLabel.classList.contains("label-move")) {
    $emailLabel.classList.add("label-move");
    $emailBox.classList.add("border-black");
  }
});
$emailInput.addEventListener("input", () => {
  if (!$emailLabel.classList.contains("label-move")) {
    $emailLabel.classList.add("label-move");
    $emailBox.classList.add("border-black");
  }
});

$emailInput.addEventListener("focusout", () => {
  if ($emailInput.value === "") {
    $emailLabel.classList.remove("label-move");
    $emailBox.classList.remove("border-black");
  }
  axios
    .post("https://doqmt.gonetis.com/signup/check-email", {
      email: $emailInput.value,
    })
    .then((response) => {
      const data = response.data;

      if (data === true) {
        // 중복있음
        $emailLabel.classList.remove("font-success");
        $emailBox.classList.remove("border-success");
        $emailLabel.classList.add("font-fail");
        $emailBox.classList.add("border-fail");
        $emailInput.classList.add("font-fail");
        $emailStatus.textContent = "Email is already in use";
        signUpCheckList.email = false;
      } else if (!emailRegExp.test($emailInput.value)) {
        // 정규표현식이 일치하지 않는 경우
        $emailLabel.classList.remove("font-success");
        $emailBox.classList.remove("border-success");
        $emailLabel.classList.add("font-fail");
        $emailBox.classList.add("border-fail");
        $emailInput.classList.add("font-fail");
        $emailStatus.textContent = "Please enter a valid email address.";
        signUpCheckList.email = false;
      } else {
        // 중복없음 + 정규표현식과 일치함
        $emailStatus.textContent = "";
        $emailInput.classList.remove("font-fail");
        $emailLabel.classList.add("font-success");
        $emailBox.classList.add("border-success");
        signUpCheckList.email = true;
      }
    })
    .catch((error) => {
      console.error(error);
    });
});

$emailCheckInput.addEventListener("focusin", () => {
  if (!$emailCheckBox.classList.contains("border-black")) {
    $emailCheckBox.classList.add("border-black");
  }
});
$emailCheckInput.addEventListener("input", () => {
  if (!$emailCheckBox.classList.contains("border-black")) {
    $emailCheckBox.classList.add("border-black");
  }
});

$emailCheckInput.addEventListener("focusout", () => {
  if ($emailCheckInput.value === "") {
    $emailCheckBox.classList.remove("border-black");
  }
});

const pwClick = () => {
  if (!$passwordLabel.classList.contains("label-move")) {
    $passwordLabel.classList.add("label-move");
    $passwordBox.classList.add("border-black");
  }
};
$passwordInput.addEventListener("focusin", pwClick);
$passwordInput.addEventListener("input", pwClick);

const pwFocusOut = () => {
  if ($passwordInput.value === "") {
    $passwordLabel.classList.remove("label-move");
    $passwordBox.classList.remove("border-black");
  }
  if (!passwordRegExp.test($passwordInput.value)) {
    // 정규표현식과 일치하지 않는 경우.
    $passwordStatus.textContent = "Password must be at least 8 characters.";
    $passwordLabel.classList.remove("font-success");
    $passwordBox.classList.remove("border-success");
    $passwordLabel.classList.add("font-fail");
    $passwordBox.classList.add("border-fail");
    signUpCheckList.password = false;
  } else {
    // 정규표현식과 일치하는 경우.
    $passwordStatus.textContent = "";
    $passwordLabel.classList.remove("font-fail");
    $passwordBox.classList.remove("border-fail");
    $passwordLabel.classList.add("font-success");
    $passwordBox.classList.add("border-success");
    signUpCheckList.password = true;
  }
};
$passwordInput.addEventListener("focusout", pwFocusOut);

// 비밀번호 확인
$passwordCheckBtn.addEventListener("click", (e) => {
  e.preventDefault();
  if (isBlindPw == true) {
    $passwordCheckImage.src = "/img/eye-slash.svg";
    $passwordInput.type = "text";
    isBlindPw = false;
    pwClick();
  } else {
    $passwordCheckImage.src = "/img/eye.svg";
    $passwordInput.type = "password";
    isBlindPw = true;
    pwFocusOut();
  }
});

// 이메일 전송
$emailSendBtn.addEventListener("click", (e) => {
  e.preventDefault();
  if (signUpCheckList.email === true) {
    $emailSendBtn.style.display = "none";
    $emailInput.readOnly = true;
    $emailInput.style.color = "#6b6b6b";
    $emailCheckBox.style.display = "flex";

    axios
      .post("https://doqmt.gonetis.com/auth-email", {
        email: $emailInput.value,
      })
      .then((response) => {
        const data = response.data;
        signUpCheckList.check = data;
      })
      .catch((error) => {
        console.error(error);
      });
  }
});

// 이메일 코드 확인
$emailCheckBtn.addEventListener("click", (e) => {
  e.preventDefault();

  if (signUpCheckList.check !== "" && $emailCheckInput.value === signUpCheckList.check) {
    // 이메일 코드가 일치하면
    $emailCheckStatus.textContent = "";
    $signupBtn.style.background = "black";
    $signupBtn.style.color = "white";
    $signupBtn.style.cursor = "pointer";
    $emailCheckBox.style.display = "none";

    signUpCheckList.emailCheck = true;
  } else {
    $emailCheckBox.classList.add("border-fail");
    $emailCheckStatus.textContent = "Code is not match.";
  }
});
