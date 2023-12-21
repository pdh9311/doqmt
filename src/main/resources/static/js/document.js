const $documentTitle = document.querySelector('.document-outer-box > h1');
const $documentAll = document.querySelectorAll('.document');


/*****************************************************************************************/

const documentMouseover = (documentInner, documentInput) => () => {
  documentInner.style.background = '#ececec';
  documentInner.style.cursor = 'pointer';
  documentInput.style.background = '#ececec';
  documentInput.style.cursor = 'pointer';
};

const documentMouseout = (documentInner, documentInput) => () => {
  documentInner.style.background = '#ffffff';
  documentInput.style.background = '#ffffff';
};

const documentClick = (username, documentName, documentId) => () => {
  location.href = `https://doqmt.gonetis.com/@${username}/doc/read?book=${bookId}&doc=${documentId}`;
};

const documentKeyup = (documentInput, documentId) => (e) => {
  if (e.keyCode === 13) {     // Enter
    // 문서명 변경 요청
    axios
        .patch(`https://doqmt.gonetis.com/@${username}/doc/title/${documentId}`, {
          title: documentInput.value,
        })
        .then((response) => {
          const data = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    documentInput.readOnly = true;
  }
};

const documentFocusout = (documentInput, documentId) => (e) => {
  // 문서명 변경 요청
  axios
      .patch(`https://doqmt.gonetis.com/@${username}/doc/title/${documentId}`, {
        title: documentInput.value,
      })
      .then((response) => {
        const data = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  documentInput.readOnly = true;
};

const documentEditClick = (documentInput) => () => {
  console.log(documentInput.value.length);
  documentInput.readOnly = false;
  documentInput.focus();
  documentInput.setSelectionRange(documentInput.value.length, documentInput.value.length);
};

const documentDelClick = (document, documentId) => () => {
  console.log(documentId);
  // 문서 삭제
  axios
      .patch(`https://doqmt.gonetis.com/@${username}/doc/delete/${documentId}`)
      .then((response) => {
        const data = response.data;
      })
      .catch((error) => {
        console.error(error);
      });

  document.style.display = 'none';
};

/*****************************************************************************************/

$documentAll.forEach(el => {
  const document = el;
  const documentInner = el.children[0];
  const documentImage = documentInner.children[0];  // TODO: 문서 이미지 변경.
  const documentInput = documentInner.children[1];
  const documentId = documentInner.children[2].children[0].value;
  const documentIdx = documentInner.children[2].children[1].value;
  const documentEdit = el.children[1];
  const documentDel = el.children[2];

  documentInner.addEventListener('mouseover', documentMouseover(documentInner, documentInput));
  documentInner.addEventListener('mouseout', documentMouseout(documentInner, documentInput));
  documentInner.addEventListener('click', documentClick(username, documentInput.value, documentId));

  documentInput.addEventListener('keyup', documentKeyup(documentInput, documentId));

  documentInput.addEventListener('focusout', documentFocusout(documentInput, documentId));

  if (documentEdit !== undefined) {
    documentEdit.addEventListener('click', documentEditClick(documentInput));
  }

  if (documentDel !== undefined) {
    documentDel.addEventListener('click', documentDelClick(document, documentId));
  }
});