const $save = document.querySelector('.save');
const $title = document.querySelector('.title');

const documentWrite = (event) => {
  event.preventDefault();

  if ($title.value !== '') {
    const content = editor.getMarkdown();
    axios
        .put(`https://doqmt.com/@${username}/doc/edit`, {   // 문서 저장 요청 url
          docId: docId,
          title: $title.value,
          content: content,
        })
        .then((response) => {
          const data = response.data;
          location.replace(`https://doqmt.com/@${username}/doc/read?book=${bookId}&doc=${docId}`);  // 작성된 문서 보기 페이지로 이동
        })
        .catch((error) => {
          console.log(error);
        });
  } else {
    alert('Please enter the title.');
  }
};

$save.addEventListener('click', documentWrite);
document.addEventListener('DOMContentLoaded', () => {
  $title.value = title;
});