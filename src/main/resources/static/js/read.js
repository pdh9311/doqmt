const $editBtn = document.querySelector('.edit-btn');

$editBtn.addEventListener('click', () => {
  location.href=`http://doqmt.com/@${username}/doc/edit?book=${bookId}&doc=${docId}`;
});