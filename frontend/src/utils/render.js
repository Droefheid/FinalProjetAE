

/**
 * setLayout allows to display specific information in an HTML template
 * containing those paramters as id to text elements (h4, h5, title)
 * @param {headerTitle} headerTitle
 * @param {footerText} footerText
 */
function setLayout(headerTitle, footerText) {
  document.querySelector("#footerText").innerText = footerText;
}
// named export
export {setLayout};