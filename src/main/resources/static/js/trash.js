const $trash = document.querySelectorAll('.trash');
$trash.forEach(el => {
  const $restore = el.children[3];
  const $delete = el.children[6];
  const bookId = el.children[7].value;
  const docId = el.children[8].value;

  console.log(bookId);
  console.log(docId);

  $restore.addEventListener('click', () => {
    // 복구
    axios
        .patch(`http://localhost:8080/@${username}/book/restore/${bookId}`)
        .then((response) => {
          const data = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    axios
        .patch(`http://localhost:8080/@${username}/doc/restore/${docId}`)
        .then((response) => {
          const data = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    el.style.display = 'none';
  });

  $delete.addEventListener('click', () => {
    // 완전 삭제
    axios
        .delete(`http://localhost:8080/@${username}/doc/${docId}`)
        .then((response) => {
          const data = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    el.style.display = 'none';
  });

});