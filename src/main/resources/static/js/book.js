const $bookTitle = document.querySelector('.book-outer-box > h1');

const $addbookNameBox = document.querySelector('.book-new-name');
const $addbookName = document.querySelector('.book-new-name > input');

const $bookBox = document.querySelector('.book-box');
const $bookAll = document.querySelectorAll('.book');

/*****************************************************************************************/

const bookMouseover = (bookInner, bookInput) => () => {
  bookInner.style.background = '#ececec';
  bookInner.style.cursor = 'pointer';
  bookInput.style.background = '#ececec';
  bookInput.style.cursor = 'pointer';
};

const bookMouseout = (bookInner, bookInput) => () => {
  bookInner.style.background = '#ffffff';
  bookInput.style.background = '#ffffff';
};

const bookClick = (username, bookName, bookId) => () => {
  location.href = `http://localhost:8080/@${username}/docs?book=${bookId}`;

};

const bookKeyup = (bookInput, bookId) => (e) => {
  if (e.keyCode === 13) {     // Enter
    // 카테고리명 변경 요청
    axios
        .patch(`http://localhost:8080/@${username}/book/name/${bookId}`, {
          "name" : bookInput.value,
        })
        .then((response) => {
          const data = response.data;
        })
        .catch((error) => {
          console.error(error);
        })
    bookInput.readOnly = true;
  }
};

const bookFocusout = (bookInput, bookId) => (e) => {
  // 카테고리명 변경 요청
  axios
      .patch(`http://localhost:8080/@${username}/book/name/${bookId}`, {
        "name" : bookInput.value,
      })
      .then((response) => {
        const data = response.data;
      })
      .catch((error) => {
        console.error(error);
      })
  bookInput.readOnly = true;
};

const bookEditClick = (bookInput) => () => {
  bookInput.readOnly = false;
  bookInput.focus();
  bookInput.setSelectionRange(bookInput.value.length, bookInput.value.length);
};

const bookDelClick = (book, bookId) => () => {
  // 카테고리 삭제
  axios
      .patch(`http://localhost:8080/@${username}/book/delete/${bookId}`)
      .then((response) => {
        const data = response.data;
        if (response.data === 0) {
          axios
              .delete(`http://localhost:8080/@${username}/book/${bookId}`)
              .then((response) => {
                const data = response.data;
              })
              .catch((error) => {
                console.error(error);
              });
        }
      })
      .catch((error) => {
        console.error(error);
      });

  book.style.display = 'none';
};



/*****************************************************************************************/

if ($addbookName !== null) {

  $addbookName.addEventListener('focusin', () => {
    $addbookName.placeholder = 'Enter the book name. And "Enter"';
  });

  $addbookName.addEventListener('focusout', () => {
    if ($addbookName.value !== '') {
      $addbookName.value = '';
    }
    $addbookName.placeholder = '+ Add book';
  });

  const addbook = (name, id, idx) => {
    const _book = document.createElement('div');
    const _bookInnerBox = document.createElement('div');
      const _bookImage = document.createElement('img');
      const _bookNameInput = document.createElement('input');
      const _span = document.createElement('span');
        const _bookIdInput = document.createElement('input');
        const _bookIdxInput = document.createElement('input');
    const _bookEditImage = document.createElement('img');
    const _bookDelImage = document.createElement('img');

    _book.classList.add('book');

    _bookInnerBox.classList.add('book-inner-box');

    _bookImage.classList.add('book-image');
    _bookImage.src = '/img/book.svg';
    _bookImage.alt = '카테고리 이미지';

    _bookNameInput.classList.add('book-name');
    _bookNameInput.type = 'text';
    _bookNameInput.value = name;
    _bookNameInput.readOnly = true;

    _bookIdInput.classList.add('book-id');
    _bookIdInput.type = 'hidden';
    _bookIdInput.value = id;
    _bookIdInput.readOnly = true;

    _bookIdxInput.classList.add('book-idx');
    _bookIdxInput.type = 'hidden';
    _bookIdxInput.value = idx;
    _bookIdxInput.readOnly = true;

    _bookEditImage.classList.add('book-edit');
    _bookEditImage.src = '/img/wand-magic-sparkles.svg';
    _bookEditImage.alt = '카테고리 이름 편집';

    _bookDelImage.classList.add('book-del');
    _bookDelImage.src = '/img/trash.svg';
    _bookDelImage.alt = '카테고리 삭제';

    _span.append(_bookIdInput, _bookIdxInput);
    _bookInnerBox.append(_bookImage, _bookNameInput, _span);
    _book.append(_bookInnerBox, _bookEditImage, _bookDelImage);
    $bookBox.prepend(_book);

    _bookInnerBox.addEventListener('mouseover', bookMouseover(_bookInnerBox, _bookNameInput));
    _bookInnerBox.addEventListener('mouseout', bookMouseout(_bookInnerBox, _bookNameInput));
    _bookInnerBox.addEventListener('click', bookClick($bookTitle.textContent, _bookNameInput.value, _bookIdInput.value));
    _bookNameInput.addEventListener('keyup', bookKeyup(_bookNameInput, _bookIdInput.value));
    _bookNameInput.addEventListener('focusout', bookFocusout(_bookNameInput, _bookIdInput.value));
    _bookEditImage.addEventListener('click', bookEditClick(_bookNameInput));
    _bookDelImage.addEventListener('click', bookDelClick(_book, _bookIdInput.value));
  };

  $addbookName.addEventListener('keyup', (e) => {
    if (e.keyCode === 13 && $addbookName.value !== '') {
      // 카테고리 저장
      axios
          .post(`http://localhost:8080/@${username}/book/add`, {
            "bookName": $addbookName.value,
          })
          .then((response) => {
            const data = response.data;
            addbook(data.bookName, data.bookId, data.idx);
          })
          .catch((error) => {
            console.error(error);
          });

      $addbookName.value = '';
      $addbookName.placeholder = '+ Add book';
    }
  });

}

/*****************************************************************************************/

$bookAll.forEach(el => {
  const book = el;
  const bookInner = el.children[0];
  const bookImage = bookInner.children[0];  // TODO: 카테고리 이미지 변경.
  const bookInput = bookInner.children[1];
  const bookId = bookInner.children[2].children[0].value;
  const bookIdx = bookInner.children[2].children[1].value;
  const bookEdit = el.children[1];
  const bookDel = el.children[2];

  bookInner.addEventListener('mouseover', bookMouseover(bookInner, bookInput));
  bookInner.addEventListener('mouseout', bookMouseout(bookInner, bookInput));
  bookInner.addEventListener('click', bookClick($bookTitle.textContent, bookInput.value, bookId));

  bookInput.addEventListener('keyup', bookKeyup(bookInput, bookId));

  bookInput.addEventListener('focusout', bookFocusout(bookInput, bookId));

  if (bookEdit !== undefined) {
    bookEdit.addEventListener('click', bookEditClick(bookInput));
  }

  if (bookDel !== undefined) {
    bookDel.addEventListener('click', bookDelClick(book, bookId));
  }
});