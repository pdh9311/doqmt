const $editBtn = document.querySelector('.edit-btn');

$editBtn.addEventListener('click', () => {
  location.href=`https://doqmt.com/@${username}/doc/edit?book=${bookId}&doc=${docId}`;
});