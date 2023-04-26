const $deleteAccountBtn = document.querySelector('.delete-btn');

$deleteAccountBtn.addEventListener('click', () => {
  const result = confirm('Are you sure you want to delete your account?');
  if (result === true) {
    axios
        .delete(`https://doqmt.com/@${username}/${memberId}`)
        .then((response) => {
          const data = response.data;
          console.log(data);
          location.replace('https://doqmt.com');
        })
        .catch((error) => {
          console.error(error);
        });
  }

});