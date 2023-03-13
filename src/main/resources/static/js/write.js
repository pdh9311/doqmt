const $save = document.querySelector('.save');
const $title = document.querySelector('.title');

const documentWrite = (event) => {
  event.preventDefault();

  if ($title.value !== '') {
    const content = editor.getMarkdown();
    axios.post(`http://43.200.252.187:8080/@${username}/doc/write`, {   // 문서 저장 요청 url
      book: bookId,
      title: $title.value,
      content: content,
    }).then((response) => {
      const documentId = response.data;
      location.replace(`http://43.200.252.187:8080/@${username}/doc/read?book=${bookId}&doc=${documentId}`);  // 작성된 문서 보기 페이지로 이동
    }).catch((error) => {
      console.log(error);
    });
  } else {
    alert('Please enter the title.');
  }
};

$save.addEventListener('click', documentWrite);

document.addEventListener('DOMContentLoaded', () => {
  $title.focus();
});