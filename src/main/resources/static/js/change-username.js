const usernameRegExp = /^[a-zA-Z0-9_-]{4,30}$/;
const $newUsername = document.querySelector('.new-username-box > input');
const $usernameCheckBtn = document.querySelector('.username-check')
const $changeUsernameBtn = document.querySelector('.change-username-btn');
const $usernameCheckResult = document.querySelector('.username-check-result');

let canChangeUsername = false;

$usernameCheckBtn.addEventListener('click', () => {
  const newUsername = $newUsername.value;
  if (newUsername !== '' && usernameRegExp.test(newUsername)) {
    axios
        .post(`http://3.35.14.80:8080/@${username}/check/username`, {
          username: newUsername,
        })
        .then((response) => {
          const data = response.data;
          console.log(data);
          if (data == true) { // 이미 존재하는 사용자명 입니다.
            $usernameCheckResult.classList.remove('font-success');
            $usernameCheckResult.classList.add('font-fail');
            $usernameCheckResult.textContent = 'Username already exists';
            $changeUsernameBtn.classList.remove('active-change-btn');
            canChangeUsername = false;
          } else {
            $usernameCheckResult.classList.remove('font-fail');
            $usernameCheckResult.classList.add('font-success');
            $usernameCheckResult.textContent = 'Username is available';
            $changeUsernameBtn.classList.add('active-change-btn');
            canChangeUsername = true;
          }
        })
        .catch((error) => {
          console.error(error);
        })
  }
});


$changeUsernameBtn.addEventListener('click', () => {
  const newUsername = $newUsername.value;
  if (canChangeUsername === true) {
    axios
        .patch(`http://3.35.14.80:8080/@${username}/username/${memberId}`, {
          username: newUsername,
        })
        .then((response) => {
          const data = response.data;
          console.log(data);
          document.querySelector('.account-info > strong').textContent = data;
        })
        .catch((error) => {
          console.error(error);
        });
  }
})