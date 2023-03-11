const {Editor} = toastui;
const {chart, codeSyntaxHighlight, colorSyntax, tableMergedCell, uml} = Editor.plugin;

// toast 인스턴스 생성
const editor = new Editor({
  el: document.querySelector('#editor'),
  height: '100%',               // 에디터 높이 100%, auto
  initialEditType: 'markdown',  // 초기 에디터 타입
  initialValue: content,             // 초기 내용
  previewStyle: 'vertical',     // 미리보기 스타일
  previewHighlight: true,       // 미리보기에 현재 작성중이곳 하이라이트
  usageStatistics: false,       // google analytics 보내지 않게 설정
  extendedAutolinks: true,      // 자동 링크 설정
  plugins: [chart, codeSyntaxHighlight, colorSyntax, tableMergedCell, uml],
  toolbarItems: [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr', 'quote'],
    ['ul', 'ol', 'task', 'indent', 'outdent'],
    ['table', 'image', 'link'],
    ['code', 'codeblock'],
    ['scrollSync'],
  ],
  customHTMLRenderer: {         // 커스텀 렌더링
    htmlBlock: {
      iframe(node) {            // iframe 렌더
        return [
          {type: 'openTag', tagName: 'iframe', outerNewLine: true, attributes: node.attrs},
          {type: 'html', content: node.childrenHTML},
          {type: 'closeTag', tagName: 'iframe', outerNewLine: true},
        ];
      },
    },
    latex(node) {               // 수학식 렌더링 - latex.js CDN 추가 해야 작동함.
      const generator = new latexjs.HtmlGenerator({hyphenate: false});
      const {body} = latexjs.parse(node.literal, {generator}).htmlDocument();

      return [
        {type: 'openTag', tagName: 'div', outerNewLine: true},
        {type: 'html', content: body.innerHTML},
        {type: 'closeTag', tagName: 'div', outerNewLine: true}
      ];
    },
  },
});