import $ from "jquery";
import {clientConfig} from "./ClientConfig";
import {theme} from "./Theme";
/**
 * Created by son on 2019-03-04.
 */
export class Renderer {
	renderErrorLogs(oResult: any): void {
		const aExceptions = oResult.exceptions;
		const aStackTraces = oResult.stackTraces;

		if (aExceptions.length === 0 && aStackTraces.length === 0) {
			return;
		}

		const rootElem = document.getElementById('error');
		let index = aExceptions.length - 1;
		let i = 0;
		while (i <= index) {
			if (i === clientConfig.removeNodeLimit) {
				break;
			}

			if (rootElem.childElementCount >= clientConfig.removeNodeLimit) {
				rootElem.removeChild(rootElem.firstElementChild);
			}

			const tr = document.createElement('tr');
			const td = document.createElement('td');
			td.innerHTML = aExceptions[index];
			td.className = 'errorItem';

			const number = '' + index;
			td.setAttribute('number', number);

			td.onclick  = function() {
				this.renderStackTrace($(window.event.currentTarget).attr('number'));
			}.bind(this);

			sessionStorage.setItem(number, aStackTraces[index]);
			tr.appendChild(td);
			rootElem.appendChild(tr);

			i++;
			index--;
		}
		this.autoScroll(1);
	}

	renderStackTrace(number: number): void {
		const rootElem = document.getElementById('stack_trace');

		while(rootElem.firstChild) {
			rootElem.removeChild(rootElem.firstChild);
		}

		const aStackTraces: string = sessionStorage.getItem(number.toString()).split('\n').toString();
		for (var i = 0; i < aStackTraces.length; i++) {
			if (aStackTraces[i] === '') {
				continue;
			}

			const details = document.createElement('details');
			details.id = 'details';
			const isSourcePackage = aStackTraces[i].indexOf(window['_info'].sourcePackage) > -1;

			if (!clientConfig.checkedLib() && !isSourcePackage) {
				console.log('라이브러리 경로는 무시합니다.');
				continue;
			}

			details.onclick = function () {
				const elem = $(window.event.currentTarget);

				// no more than 2 call ajax
				if (elem.children().length >= 2) {
					return;
				}
				let text = elem.children[0].textContent;

				const fileInfo = text.substring(text.indexOf('('), text.length).replace('(', '').replace(')', '');
				const fileName = fileInfo.split('.')[0];
				const line = fileInfo.split(':')[1];

				var successCallback = function (aResponse) {
					const lineNumber = line;
					if (aResponse.length === 0) {
						var p = document.createElement('p');
						p.className = 'fileInfo';
						p.innerHTML = "Library files can not do code analysis.";
						elem.append(p);
						$('#loading').css('display', 'none');
						return;
					}

					for (var i = 0; i < aResponse.length; i++) {
						const p = document.createElement('p');
						p.className = 'codestyle';

						if (lineNumber === aResponse[i].substring(0, aResponse[i].indexOf('&nbsp') - 1)) {
							$(p).css('color: red; font-weight: bold');
						} else {
							aResponse[i] = theme.apply(aResponse[i]);
						}
						if (!aResponse[i + 1]) {
							p.innerHTML = aResponse[i];
						} else {
							p.innerHTML = aResponse[i] + '</br>';
						}
						elem.append(p);
					}
					$('#loading').css('display', 'none');
				}.bind(this);
				// parser.viewCode(fileName, line, 10, successCallback);
			};

			const elem = document.createElement('summary');
			elem.innerHTML = aStackTraces[i];

			if (isSourcePackage) {
				$(elem).css('color: #BBBB00');
			}
			details.appendChild(elem);
			rootElem.appendChild(details);
		}
	}

	renderLogs(logs: string): void {
		const aLogs = logs.split('\r\t');
		const length = aLogs.length;

		const tbody = document.getElementById('logs').children[0];
		for (var i = 0; i < length; i++) {
			if (tbody.childElementCount >= clientConfig.removeNodeLimit) {
				tbody.removeChild(tbody.firstElementChild);
			}

			const re = /.+(.java|.class)(.*?)\)/g;
			const tr = document.createElement('tr');
			const td = document.createElement('td');

			$(tr).css('text-indent: 10px; !important');
			const text = aLogs[i];

			const span = document.createElement('span');
			if (text.indexOf('\\a\\r') > -1) {
				$(span).css('color: #BBBB00');
				span.innerHTML = text.substring('\\a\\r'.length, text.indexOf('</br>'));
				td.innerHTML = span.outerHTML + text.substring(text.indexOf('</br>'), text.length);
			} else {
				var regexText = re.exec(text);
				if (!!regexText && !!regexText[0]) {
					$(span).css('color: beige');
					span.innerHTML = regexText[0];
					td.innerHTML = span.outerHTML + text.substring(regexText[0].length, text.length);
				} else {
					td.innerHTML = text;
				}
			}

			tr.appendChild(td);
			tbody.appendChild(tr);
		}
		this.autoScroll(2);
	}

	autoScroll(i: number): void {
		const divTable = document.getElementsByClassName('table-responsive')[i];
		divTable.scrollTop = divTable.scrollHeight;
	}

	renderUsage(oData: any): void {
		const cpu = oData.cpu;
		const memory = oData.memory;
		const usageElem = document.getElementById('usage');

		let i = 0, j = 0;
		const acrInterval = setInterval (function() {
			usageElem.innerHTML = i + '/' + j;
			if( i === cpu && j === memory) {
				clearInterval(acrInterval);
			}
			i === cpu ? i = cpu : i++;
			j === memory ? j = memory : j++;
		}, 15);
	}


	drawInfos(oData: any): void {
		const version = oData.version;
		const branch = oData.branch;
		const infosElem = document.getElementById('infos');
		infosElem.innerHTML = version + '<br>' + branch;
	}

	drawAccessLogs(data: any): void {
		const elem = document.getElementById('accessLogs');
		data.accessLogs.forEach(function (item) {
			if (elem.childElementCount >= clientConfig.removeNodeLimit) {
				elem.removeChild(elem.firstElementChild);
			}
			const tr = document.createElement('tr');
			const td = document.createElement('td');
			td.innerHTML = item;

			tr.appendChild(td);
			elem.appendChild(tr);
		});
		this.autoScroll(0);
	}

	updateChart(oSuccess: any, oFail: any): void {
		// if (chartConfig.data.datasets[0].data.length >= clientConfig.chartDataLimit || chartConfig.data.datasets[1].data.length >= clientConfig.chartDataLimit) {
		// 	chartConfig.data.datasets[0].data.shift();
		// 	chartConfig.data.datasets[1].data.shift();
		// }
        //
        // chartConfig.data.datasets[0].data.push(oSuccess);
        // chartConfig.data.datasets[1].data.push(oFail);
        //
        // window.chart.update();
	}

	drawChart(chartConfig: any): void {
		// var ctx = document.getElementById("canvas").getContext("2d");
		// window.chart = new Chart(ctx, chartConfig);
	}

	renderSetting(data: any): void {
		const table = document.getElementById('settingTable');
		for (const i in data) {
			const tr = document.createElement('tr');
			const key = document.createElement('td');
			const value = document.createElement('input');
			value.className = 'setting_values';
			value.value = data[i];
			for (var j = 0; j < clientConfig.readOnlyKeys.length; j++) {
				if (i === clientConfig.readOnlyKeys[j]) {
					// value: HTMLTableDataCellElement = document.createElement('td');
					break;
				}
			}

			if (value.nodeName === 'TD') {
				value.innerText = data[i];
			}
			key.className = 'setting_keys';
			key.innerText = i;

			tr.appendChild(key);
			tr.appendChild(value);

			table.appendChild(tr);
		}
	}
}
