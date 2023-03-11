const $userBox = document.querySelector('.user-box');
const $userClickBox = document.querySelector('.user-click-box');
let userClickBox = false;

if ($userBox !== null) {
  $userBox.addEventListener('click', () => {
    if (userClickBox) {
      $userClickBox.style.display = "none";
      userClickBox = false;
    } else {
      $userClickBox.style.display = "flex";
      userClickBox = true;
    }
  });
}
