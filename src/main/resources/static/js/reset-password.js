const checkList = {
  email: false,
  auth: false,
  password: false,
  authCode: '',
  newPw: '',
  againPw: '',
  seeNewPw: false,
  seeAgainPw: false,
}
const passwordRegExp = /^[a-zA-Z0-9`~!@#$%^&*()\[\]\\\|{};':",./<>?_+=-]{8,}$/;

const $title = document.querySelector('h2');
const $comment = document.querySelector('.comment');

const $emailBox = document.querySelector('.email-box');
const $emailInput = document.querySelector('#email');
const $emailLabel = document.querySelector('.email-box > label');

const $authBox = document.querySelector('.auth-box');
const $authInput = document.querySelector('#auth');
const $authLabel = document.querySelector('.auth-box > label');

const $pwBox = document.querySelector('.pw-box');
const $pwInput = document.querySelector('#pw');
const $pwLabel = document.querySelector('.pw-box > label');

const $pwAgainBox = document.querySelector('.pw-again-box');
const $pwAgainInput = document.querySelector('#pw-again');
const $pwAgainLabel = document.querySelector('.pw-again-box > label');

const $pwCheckBox = document.querySelector('.pw-check-box');
const $pwCheckSvg = document.querySelector('.pw-check-status > svg');
const $pwCheckSpan = document.querySelector('.pw-check-status > span');

const $continueBtn = document.querySelector('.continue-btn');


const active = (input, label) => () => {
  input.classList.remove('border-gray');
  input.classList.add('border-black');
  label.classList.add('label-move');
};

const noActive = (input, label) => () => {
  if (input.value === '') {
    input.classList.remove('border-black');
    label.classList.remove('label-move');
    input.classList.add('border-gray');
  }
}

const pwInputActive = (box, label, input) => () => {
  box.classList.remove('border-gray');
  box.classList.add('border-black');
  label.classList.add('pw-label-move');
  $pwCheckBox.style.display = 'flex';
  if (passwordRegExp.test(input.value)) {
    $pwCheckSvg.classList.remove('fill-fail');
    $pwCheckSpan.classList.remove('font-fail');
    $pwCheckSvg.classList.add('fill-success');
    $pwCheckSpan.classList.add('font-success');
  }
};

const pwFocusInActive = (box, label) => () => {
  box.classList.remove('border-gray');
  box.classList.add('border-black');
  label.classList.add('pw-label-move');
};

const newPwFocusOutActive = (box, label, input) => () => {
  if (input.value === '') {
    box.classList.remove('border-black');
    label.classList.remove('pw-label-move');
    box.classList.add('border-gray');
  } else if (!passwordRegExp.test(input.value)) {
    $pwCheckSvg.classList.remove('fill-success');
    $pwCheckSpan.classList.remove('font-success');
    $pwCheckSvg.classList.add('fill-fail');
    $pwCheckSpan.classList.add('font-fail');
  } else {
    $pwCheckSvg.classList.remove('fill-fail');
    $pwCheckSpan.classList.remove('font-fail');
    $pwCheckSvg.classList.add('fill-success');
    $pwCheckSpan.classList.add('font-success');
  }
  checkList.newPw = $pwInput.value;
}

const againPwFocusOutActive = (box, label, input) => () => {
  if (input.value === '') {
    box.classList.remove('border-black');
    label.classList.remove('pw-label-move');
    box.classList.add('border-gray');
  } else if (!passwordRegExp.test(input.value)) {
    $pwCheckSvg.classList.remove('fill-success');
    $pwCheckSpan.classList.remove('font-success');
    $pwCheckSvg.classList.add('fill-fail');
    $pwCheckSpan.classList.add('font-fail');
  } else {
    $pwCheckSvg.classList.remove('fill-fail');
    $pwCheckSpan.classList.remove('font-fail');
    $pwCheckSvg.classList.add('fill-success');
    $pwCheckSpan.classList.add('font-success');
  }
  checkList.againPw = $pwAgainInput.value;
}


$emailInput.addEventListener('input', active($emailInput, $emailLabel));
$emailInput.addEventListener('focusin', active($emailInput, $emailLabel));
$emailInput.addEventListener('focusout', noActive($emailInput, $emailLabel));

$authInput.addEventListener('input', active($authInput, $authLabel));
$authInput.addEventListener('focusin', active($authInput, $authLabel));
$authInput.addEventListener('focusout', noActive($authInput, $authLabel));

$pwInput.addEventListener('input', pwInputActive($pwBox, $pwLabel, $pwInput));
$pwInput.addEventListener('focusin', pwFocusInActive($pwBox, $pwLabel));
$pwInput.addEventListener('focusout', newPwFocusOutActive($pwBox, $pwLabel, $pwInput));

$pwAgainInput.addEventListener('input', pwInputActive($pwAgainBox, $pwAgainLabel, $pwAgainInput));
$pwAgainInput.addEventListener('focusin', pwFocusInActive($pwAgainBox, $pwAgainLabel));
$pwAgainInput.addEventListener('focusout', againPwFocusOutActive($pwAgainBox, $pwAgainLabel, $pwAgainInput));

$continueBtn.addEventListener('click', () => {
  if (checkList.email === false) {
    const email = $emailInput.value;
    $emailBox.style.display = 'none';
    $authBox.style.display = 'block';
    $comment.textContent = `Please check your email for the authentication code to reset your password. (${email})`;
    checkList.email = true;
    axios
        .post('https://doqmt.com/auth-email', {
          email: email,
        })
        .then((response) => {
          const data = response.data;
          checkList.authCode = data;
        })
        .catch((error) => {
          console.error(error);
        });
  } else if (checkList.auth === false && $authInput.value !== '' && checkList.authCode === $authInput.value) {
    $authBox.style.display = 'none';
    $pwBox.style.display = 'flex';
    $pwAgainBox.style.display = 'flex';
    $title.textContent = 'Change Your Password';
    $comment.textContent = 'Enter a new password below to change your password.';
    $continueBtn.textContent = 'Reset password';
    checkList.auth = true;
  } else if (checkList.newPw !== checkList.againPw) {
    $pwCheckSvg.classList.remove('fill-success');
    $pwCheckSpan.classList.remove('font-success');
    $pwCheckSvg.classList.add('fill-fail');
    $pwCheckSpan.classList.add('font-fail');
    $pwCheckSpan.textContent = 'Passwords do not match';
  } else if (checkList.newPw !== '' && checkList.newPw === checkList.againPw) {
    axios
        .patch(`https://doqmt.com/reset-password`, {
          email: $emailInput.value,
          newPassword: $pwInput.value,
        })
        .then((response) => {
          const data = response.data;
          if (data === 'ok') {
            location.replace('https://doqmt.com/signin');
          } else if (data === 'fail') {
            $pwCheckSpan.textContent = 'Email not found';
            $pwCheckSvg.classList.remove('fill-success');
            $pwCheckSpan.classList.remove('font-success');
            $pwCheckSvg.classList.add('fill-fail');
            $pwCheckSpan.classList.add('font-fail');
          }
        })
        .catch((error) => {
          console.error(error);
        });
  }
});

const $newPwSeeBtn = document.querySelector('.pw-box > .see-password');
const $newPwSeeImg = document.querySelector('.pw-box > .see-password > img');
const $againPwSeeBtn = document.querySelector('.pw-again-box > .see-password');
const $againPwSeeImg = document.querySelector('.pw-again-box > .see-password > img');
$newPwSeeBtn.addEventListener('click', () => {
  if (checkList.seeNewPw === false) {
    $pwInput.type = 'text';
    $newPwSeeImg.src = '/img/eye-slash.svg';
    checkList.seeNewPw = true;
  } else {
    $pwInput.type = 'password';
    $newPwSeeImg.src = '/img/eye.svg';
    checkList.seeNewPw = false;
  }
});

$againPwSeeBtn.addEventListener('click', () => {
  if (checkList.seeAgainPw === false) {
    $pwAgainInput.type = 'text';
    $againPwSeeImg.src = '/img/eye-slash.svg';
    checkList.seeAgainPw = true;
  } else {
    $pwAgainInput.type = 'password';
    $againPwSeeImg.src = '/img/eye.svg';
    checkList.seeAgainPw = false;
  }
});
