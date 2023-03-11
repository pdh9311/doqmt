const $profileImage = document.querySelector('#profile-file');
const $userFace = document.querySelector('.user-face');
const $userBoxFace = document.querySelector('.user-box > img');

$profileImage.addEventListener('change', () => {
  const formData = new FormData();
  formData.append("file", $profileImage.files[0]);

  axios
      .post(`http://3.35.14.80:8080/@${username}/profile-image/${memberId}`, formData)
      .then((response) => {
        const data = response.data;
        $userFace.src = data;
        $userBoxFace.src = data;
      })
      .catch((error) => {
        console.error(error);
      });

});
