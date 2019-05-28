/**
 * Created by son on 2019-03-07.
 */
import $ from 'jquery';

export class Setting {
	constructor() {

	}
	public onToggleLibEvent(): void {
		var isCheckedLib= $('input[id=libCheck]').is(':checked');
		if (!isCheckedLib) {
			$('input[id=libCheck]').trigger('click');
		}
	}

	public onToggleLogEvent(): void {
		var isCheckedLog = $('input[id=logCheck]').is(':checked');
		if (!isCheckedLog) {
			$('input[id=logCheck]').trigger('click');
		}
	}

	public onToggleErrorEvent(): void {
		var isCheckedError = $('input[id=errorCheck]').is(':checked');
		if (!isCheckedError) {
			$('input[id=errorCheck]').trigger('click');
		}
	}

	public onToggleAccessEvent(): void {
		var isCheckedAccess= $('input[id=accessCheck]').is(':checked');
		if (!isCheckedAccess) {
			$('input[id=accessCheck]').trigger('click');
		}
	}

	public dropDowns(): void {
		document.getElementById("options").classList.toggle("show");
	}
}

// window.onclick = function(e: Event) {
// 	const target = e.target as HTMLElement;
// 	if (!target.matches('.dropbtn')) {
// 		var options = document.getElementById("options");
// 		if (!target.matches('.slider') && !target.matches('input') && options.classList.contains('show')) {
// 			options.classList.remove('show');
// 		}
// 	}
//
// 	var stackTraces = document.getElementById('stack_trace');
//
// 	if (target.matches('.errorItem') || target.matches('summary') || target.matches('.codestyle')) {
// 		return;
// 	}
//
// 	if (!!stackTraces && stackTraces.firstElementChild !== null && stackTraces.firstElementChild.tagName === 'DETAILS') {
// 		while (stackTraces.childElementCount > 0) {
// 			stackTraces.removeChild(stackTraces.firstElementChild)
// 		}
//
// 		var header = document.createElement('h4');
// 		header.innerHTML = '<h4 class="container_headers header-title mt-0">Please select error log in table</h4>';
// 		stackTraces.appendChild(header);
// 	}
// };