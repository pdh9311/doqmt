const $emailBox = document.querySelector('.email-box');
const $emailInput = document.querySelector('.email-box > input');
const $emailLabel = document.querySelector('.email-box > label');

const $passwordBox = document.querySelector('.password-box');
const $passwordInput = document.querySelector('.password-box > input');
const $passwordLabel = document.querySelector('.password-box > label');

const $passwordCheck = document.querySelector('.password-box > span');
const $passwordCheckImage = document.querySelector('.password-box > span > img');
let pw = true;

$emailBox.addEventListener('input', () => {
  if (!$emailLabel.classList.contains('label-move')) {
    $emailLabel.classList.add('label-move');
    $emailBox.classList.add('border-black');
  }
});

$emailBox.addEventListener('focusin', () => {
  if (!$emailLabel.classList.contains('label-move')) {
    $emailLabel.classList.add('label-move');
    $emailBox.classList.add('border-black');
  }
});

$emailInput.addEventListener('focusout', () => {
  if ($emailInput.value === '') {
    $emailLabel.classList.remove('label-move');
    $emailBox.classList.remove('border-black');
  }
});

$passwordBox.addEventListener('input', () => {
  if (!$passwordLabel.classList.contains('label-move')) {
    $passwordLabel.classList.add('label-move');
    $passwordBox.classList.add('border-black');
  }
});

$passwordBox.addEventListener('focusin', () => {
  if (!$passwordLabel.classList.contains('label-move')) {
    $passwordLabel.classList.add('label-move');
    $passwordBox.classList.add('border-black');
  }
});

$passwordInput.addEventListener('focusout', () => {
  if ($passwordInput.value === '') {
    $passwordLabel.classList.remove('label-move');
    $passwordBox.classList.remove('border-black');
  }
});

$passwordCheck.addEventListener('click', () => {
  if (pw == true) {
    $passwordCheckImage.src = '/img/eye-slash.svg';
    $passwordInput.type = 'text';
    pw = false;
  } else {
    $passwordCheckImage.src = '/img/eye.svg';
    $passwordInput.type = 'password';
    pw = true;
  }
});
