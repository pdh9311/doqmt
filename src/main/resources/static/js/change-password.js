const changePw = {
  currentPw: '',
  newPw: '',
  againPw: '',
  result: [false, false, false],
}

const passwordRegExp = /^[a-zA-Z0-9`~!@#$%^&*()\[\]\\\|{};':",./<>?_+=-]{8,}$/;

const $currentPwInput = document.querySelector('#current-pw');
const $currentPwLabel = document.querySelector('.current-pw-box > label');
const $currentPwStatus = document.querySelector('.current-pw-status');

const $newPwInput = document.querySelector('#new-pw');
const $newPwLabel = document.querySelector('.new-pw-box > label');
const $newPwStatus = document.querySelector('.new-pw-status');

const $againPwInput = document.querySelector('#again-pw');
const $againPwLabel = document.querySelector('.again-pw-box > label');
const $againPwStatus = document.querySelector('.again-pw-status');

const $changePwBtn = document.querySelector('.change-password-btn');

const $incorrect = document.querySelector('.incorrect');

const canChange = () => {
  if (changePw.result[0] === true &&
      changePw.result[1] === true &&
      changePw.result[2] === true) {
    $changePwBtn.classList.add('active-change-btn');
  } else {
    $changePwBtn.classList.remove('active-change-btn');
  }
}

$currentPwInput.addEventListener('focusin', () => {
  $currentPwLabel.classList.add('label-move');
  $currentPwInput.classList.remove('border-gray');
  $currentPwInput.classList.add('border-black');
});
$currentPwInput.addEventListener('input', () => {
  const currentPw = $currentPwInput.value;
  changePw.currentPw = currentPw;
  if (currentPw === '') {  // current만 에러처리
    $currentPwLabel.classList.remove('label-move');
    $currentPwInput.classList.remove('border-black');
    $currentPwInput.classList.add('border-fail');
    $currentPwLabel.classList.add('font-fail');
    $currentPwStatus.textContent = 'Please enter your current password';
    changePw.result[0] = false;
  } else if (changePw.newPw !== '' && changePw.newPw === currentPw) {  // newPw에 달라야한다고 에러처리
    $newPwInput.classList.remove('border-black');
    $newPwInput.classList.add('border-fail');
    $newPwLabel.classList.add('font-fail');
    $newPwStatus.textContent = 'Must be different to current password';
    changePw.result[1] = false;
  } else {
    $currentPwLabel.classList.remove('font-fail');
    $currentPwInput.classList.remove('border-fail');
    $currentPwInput.classList.add('border-black');
    $currentPwInput.classList.add('border-gray');
    $currentPwStatus.textContent = '';
    changePw.result[0] = true;
  }
  canChange();
});

$newPwInput.addEventListener('focusin', () => {
  $newPwLabel.classList.add('label-move');
  $newPwInput.classList.remove('border-gray');
  $newPwInput.classList.add('border-black');
});
$newPwInput.addEventListener('input', () => {
  const newPw = $newPwInput.value;
  changePw.newPw = newPw;
  if (newPw === '') {   // Please enter your new password
    $newPwLabel.classList.remove('label-move');
    $newPwInput.classList.remove('border-black');
    $newPwInput.classList.add('border-fail');
    $newPwLabel.classList.add('font-fail')
    $newPwStatus.textContent = 'Please enter your new password';
    changePw.result[1] = false;
  } else if (!passwordRegExp.test(newPw)) { // 정규식과 일치하지 않는 경우
    $newPwInput.classList.add('border-fail');
    $newPwLabel.classList.add('font-fail')
    $newPwStatus.textContent = 'Password must be at least 8 characters.';
    changePw.result[1] = false;
  } else if (changePw.currentPw !== '' && changePw.currentPw === newPw) {  // Must be different to current password
    $newPwInput.classList.add('border-fail');
    $newPwLabel.classList.add('font-fail')
    $newPwStatus.textContent = 'Must be different to current password';
    changePw.result[1] = false;
  } else if (changePw.againPw !== '' && changePw.againPw !== newPw) {
    $againPwLabel.classList.add('font-fail');
    $againPwInput.classList.add('border-fail');
    $againPwStatus.textContent = 'Passwords do not match';
    changePw.result[2] = false;

    $newPwLabel.classList.remove('font-fail');
    $newPwInput.classList.remove('border-fail');
    $newPwInput.classList.add('border-gray');
    $newPwStatus.textContent = '';
    changePw.result[1] = true;
  } else if (changePw.againPw !== '' && changePw.againPw === newPw) {
    $againPwLabel.classList.remove('font-fail');
    $againPwInput.classList.remove('border-fail');
    $againPwInput.classList.add('border-gray');
    $againPwStatus.textContent = '';
    changePw.result[2] = true;

    $newPwLabel.classList.remove('font-fail');
    $newPwInput.classList.remove('border-fail');
    $newPwInput.classList.add('border-gray');
    $newPwStatus.textContent = '';
    changePw.result[1] = true;
  } else {
    $newPwLabel.classList.remove('font-fail');
    $newPwInput.classList.remove('border-fail');
    $newPwInput.classList.add('border-gray');
    $newPwStatus.textContent = '';
    changePw.result[1] = true;
  }
  canChange();
});

$againPwInput.addEventListener('focusin', () => {
  $againPwLabel.classList.add('label-move');
  $againPwInput.classList.remove('border-gray');
  $againPwInput.classList.add('border-black');
});
$againPwInput.addEventListener('input', () => {
  const againPw = $againPwInput.value;
  changePw.againPw = againPw;
  if (againPw === '') {   // Please enter your again password
    $againPwLabel.classList.remove('label-move');
    $againPwInput.classList.remove('border-black');
    $againPwLabel.classList.add('font-fail');
    $againPwInput.classList.add('border-fail');
    $againPwStatus.textContent = 'Please enter your again password';
    changePw.result[2] = false;
  } else if (!passwordRegExp.test(againPw)) {  // 정규식과 일치하지 않는 경우
    $againPwLabel.classList.add('font-fail');
    $againPwInput.classList.add('border-fail');
    $againPwStatus.textContent = 'Password must be at least 8 characters.';
    changePw.result[2] = false;
  } else if (againPw !== changePw.newPw) {  // 비밀번호가 일치하지 않는 경우
    $againPwLabel.classList.add('font-fail');
    $againPwInput.classList.add('border-fail');
    $againPwStatus.textContent = 'Passwords do not match';
    changePw.result[2] = false;
  } else {
    $againPwLabel.classList.remove('font-fail');
    $againPwInput.classList.remove('border-fail');
    $againPwInput.classList.add('border-gray');
    $againPwStatus.textContent = '';
    changePw.result[2] = true;
  }
  canChange();
});

$changePwBtn.addEventListener('click', () => {
  if (changePw.result[0] === true &&
      changePw.result[1] === true &&
      changePw.result[2] === true) {
    axios
        .patch(`http://doqmt.com/@${username}/password/${memberId}`, {
          currentPw: changePw.currentPw,
          newPw: changePw.newPw,
          againPw: changePw.againPw,
        })
        .then((response) => {
          const data = response.data;
          if (data === false) {
            $incorrect.style.display = 'flex';
          } else {
            // 로그 아웃됨.
            location.href = 'http://doqmt.com';
          }
        })
        .catch((error) => {
          console.error(error);
        });
  }
});