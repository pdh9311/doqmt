const Viewer = toastui.Editor;
const {chart, codeSyntaxHighlight, colorSyntax, tableMergedCell, uml} = Viewer.plugin;

const basic = `![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png)\n\n# Awesome Editor!\n\nIt has been _released as opensource in 2018_ and has ~~continually~~ evolved to **receive 10k GitHub ⭐️ Stars**.\n\n## Create Instance\n\nYou can create an instance with the following code and use \`getHtml()\` and \`getMarkdown()\` of the [Editor](https://github.com/nhn/tui.editor).\n\n\`\`\`js\nconst editor = new Editor(options);\n\`\`\`\n\n> See the table below for default options\n> > More API information can be found in the document\n\n| name | type | description |\n| --- | --- | --- |\n| el | \`HTMLElement\` | container element |\n\n## Features\n\n* CommonMark + GFM Specifications\n   * Live Preview\n   * Scroll Sync\n   * Auto Indent\n   * Syntax Highlight\n        1. Markdown\n        2. Preview\n\n## Support Wrappers\n\n> * Wrappers\n>    1. [x] React\n>    2. [x] Vue\n>    3. [ ] Ember`;
const colorSyntaxPlugin = `## Color Syntax Plugin\n\n<span style="color:#86c1b9">Click the color picker button on the toolbar!</span>`;
const tableMergedCellPlugin = `## Table Merged Cell Plugin\n\n| @cols=2:merged |\n| --- | --- |\n| table | table2 |`;
const codeSyntaxHighlightingPlugin = `## Code Syntax Highlighting Plugin\n\n\`\`\`javascript\nconsole.log('foo')\n\`\`\`\n\`\`\`html\n<div id="editor"><span>baz</span></div>\n\`\`\``;
const chartPlugin = `## Chart Plugin\n\n$$chart\n,book1,book2\nJan,21,23\nFeb,31,17\n\ntype: column\ntitle: Monthly Revenue\nx.title: Amount\ny.title: Month\ny.min: 1\ny.max: 40\ny.suffix: $\n$$`;
const umlPlugin = `## UML Plugin\n\n$$uml\npartition Conductor {\n  (*) --> "Climbs on Platform"\n  --> === S1 ===\n  --> Bows\n}\n\npartition Audience #LightSkyBlue {\n  === S1 === --> Applauds\n}\n\npartition Conductor {\n  Bows --> === S2 ===\n  --> WavesArmes\n  Applauds --> === S2 ===\n}\n\npartition Orchestra #CCCCEE {\n  WavesArmes --> Introduction\n  --> "Play music"\n}\n$$`;
const latex = `## LaText\n\n$$latex\n\\documentclass{article}\n\\begin{document}\n\n$\nf(x) = \\int_{-\\infty}^\\infty \\hat f(\\xi),e^{2 \\pi i \\xi x} , d\\xi\n$\n\\end{document}\n$$`;

const viewer = new Viewer({
  el: document.querySelector('#viewer'),
  view: true,
  height: '100%',
  /*initialValue: basic + '\n' +colorSyntaxPlugin + '\n' + tableMergedCellPlugin + '\n' + codeSyntaxHighlightingPlugin + '\n' + chartPlugin + '\n' + umlPlugin + '\n' + latex,*/
  initialValue: content,
  plugins: [chart, codeSyntaxHighlight, colorSyntax, tableMergedCell, uml],
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

window.addEventListener('DOMContentLoaded', function() {
  document.querySelector('.toastui-editor-toolbar').style.display = 'none';
  document.querySelector('.toastui-editor').style.display = 'none';
  document.querySelector('.toastui-editor-md-splitter').style.display = 'none';
  document.querySelector('.toastui-editor-ww-container').style.display = 'none';
  document.querySelector('.toastui-editor-mode-switch').style.display = 'none';
  document.querySelector('.toastui-editor-context-menu').style.display = 'none';
});