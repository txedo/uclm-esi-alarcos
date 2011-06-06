/** Takes two HTML divs ID as arguments and swaps its visibility.
 * @param toHide The HTML div ID that will be hidden.
 * @param toShow The HTML div ID that will be shown.
 */
function swapDivVisibility (toHide, toShow) {
	document.getElementById(toHide).style.display='none';
	document.getElementById(toShow).style.display='';
}

