const usernameRegExp = /^[a-zA-Z0-9_-]{4,30}$/;
const $newUsername = document.querySelector('.new-username-box > input');
const $usernameCheckBtn = document.querySelector('.username-check')
const $changeUsernameBtn = document.querySelector('.change-username-btn');
const $usernameCheckResult = document.querySelector('.username-check-result');
const $home = $userClickBox.querySelector('a:nth-child(1)');
const $accountSettings = $userClickBox.querySelector('a:nth-child(2)');
const $trash = $userClickBox.querySelector('a:nth-child(3)');

let canChangeUsername = false;

$usernameCheckBtn.addEventListener('click', () => {
  const newUsername = $newUsername.value;
  if (newUsername !== '' && usernameRegExp.test(newUsername)) {
    axios
        .post(`https://doqmt.com/@${username}/check/username`, {
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
        .patch(`https://doqmt.com/@${username}/username/${memberId}`, {
          username: newUsername,
        })
        .then((response) => {
          const data = response.data;
          console.log(data);
          username = data;
          document.querySelector('.account-info > strong').textContent = username;
          $username.textContent = username;
          $home.href = `https://doqmt.com/@${username}`;
          $accountSettings.href = `https://doqmt.com/@${username}/profile/setting`;
          $trash.href = `https://doqmt.com/@${username}/trash`;
        })
        .catch((error) => {
          console.error(error);
        });
  }
})