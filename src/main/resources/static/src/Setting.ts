/**
 * Created by son on 2019-03-07.
 */
import $ from 'jquery';

export class Setting {
	public init(): void {
		const toggleButtons = ['logCheck', 'accessCheck', 'errorCheck'];
		toggleButtons.forEach( (id) => {
			$('input[id=' + id + ']').trigger('click');
		});
	}

	/**
	 * 토글 이벤트
	 * @param elem
	 */
	public onToggleEvent(elem: any): void {
		if (!elem.is(':checked')) {
			elem.triggerHandler('click');
		}
	}

	/**
	 * 옵션 버튼 클릭했을 때,
	 */
	public dropDowns(): void {
		document.getElementById("options").classList.toggle("show");
	}

	/**
	 * 엘리먼트 제거
	 * @param target
	 */
	public clear(target: any): void {
		const dropDowns = ['#options', '#dropdown-lang'];
		dropDowns.forEach( (n) => {
			const el = $(n);
			if (target.closest(n).length === 0 && el.attr('class').indexOf('show') > -1) {
				el.removeClass('show');
			}
		});
		return;

		const stackTraces = $('#stack_trace');
		const header = document.createElement('h4');
		header.innerHTML = '<h4 class="container_headers header-title mt-0">Please select error log in table</h4>';
		stackTraces.append(header);
	}
}
