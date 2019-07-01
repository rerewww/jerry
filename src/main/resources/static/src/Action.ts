/**
 * Created by son on 2019-03-09.
 */
import $ from 'jquery';
import {Renderer} from "./Renderer";
export class Action {
	public getInfos(): void {
		const self = this;
		$.ajax({
			url: 'getInfos.son',
			dataType: 'json',
			success: function (response) {
				Renderer.drawInfos(response);
			}
		})
	}

	public setDefaultEnvs(data: any): void {
		for (var i in data) {
			window['_info'].defaultEnvs[i] = data[i];
		}
	}

	public reset(): void {
		const data = window['_info'].defaultEnvs;
		const table = document.getElementById('settingTable') ;
		for (var i =  0; i < table.childElementCount; i++) {
			if (table.children[i].lastElementChild.nodeName === "TD") {
				continue;
			}
			$(table.children[i].lastElementChild).text = data[table.children[i].firstChild.textContent];
		}
	}

	public apply(): void {
		const data = {};
		const table = document.getElementById('settingTable');
		for (var i = 0; i < table.childElementCount; i++) {
			if (table.children[i].lastElementChild.nodeName === "TD") {
				continue;
			}
			data[table.children[i].firstChild.textContent] = $(table.children[i].lastElementChild).text;
		}
		$.ajax({
			url: 'apply.son',
			type: 'POST',
			async: true,
			contentType: "application/json;charset=UTF-8",
			traditional: true,
			data: JSON.stringify(data),
			dataType: 'json',
			success: function (response) {
				// alert
			}
		})
	}

	public viewCode(fileName: string, line: string, range: number, successCallback: any): void {
		if (!line) {
			return;
		}

		$('#loading').css('display', 'block');
		$.ajax({
			url: '/viewCode.son',
			type: 'GET',
			async: true,
			data: {
				fileName: fileName,
				line: line,
				range: $('#codeRange').val()
			},
			dataType: 'json',
			success: successCallback
		})
	}
}
