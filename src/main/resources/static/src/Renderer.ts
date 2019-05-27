import {clientConfig} from "./ClientConfig";
import {theme} from "./Theme";
/**
 * Created by son on 2019-03-04.
 */
export class Renderer {
	constructor() {

	}
	drawErrorElem(oResult: any): void {
		var aExceptions = oResult.exceptions;
		var aStackTraces = oResult.stackTraces;

		if (aExceptions.length === 0 && aStackTraces.length === 0) {
			return;
		}
		var rootElem = document.getElementById('error');

		var index = aExceptions.length - 1;
		var i = 0;
		while (i <= index) {
			// 제한 크기와 같으면 에러 로그 삽입을 중지한다.
			if (i === clientConfig.removeNodeLimit) {
				break;
			}

			if (rootElem.childElementCount >= clientConfig.removeNodeLimit) {
				rootElem.removeChild(rootElem.firstElementChild);
			}

			var tr = document.createElement('tr');
			var td = document.createElement('td');
			td.innerHTML = aExceptions[index];
			td.className = 'errorItem';

			var number = '' + index;
			td.setAttribute('number', number);

			td.onclick  = function() {
				// this.drawStackTrace(window.event.currentTarget.getAttribute('number'));
			}.bind(this);

			sessionStorage.setItem(number, aStackTraces[index]);
			tr.appendChild(td);
			rootElem.appendChild(tr);

			i++;
			index--;
		}
		this.autoScroll(1);
	}

	/*
	drawStackTrace(number: number): void {
		var rootElem = document.getElementById('stack_trace');

		while(rootElem.firstChild) {
			rootElem.removeChild(rootElem.firstChild);
		}

		var aStackTraces = sessionStorage.getItem(number).split('\n');

		for (var i = 0; i < aStackTraces.length; i++) {
			if (aStackTraces[i] === '') {
				continue;
			}

			var details = document.createElement('details');
			details.id = 'details';
			var isSourcePackage = aStackTraces[i].indexOf(_info.sourcePackage) > -1;

			if (!clientConfig.checkedLib() && !isSourcePackage) {
				console.log('라이브러리 경로는 무시합니다.');
				continue;
			}

			details.onclick = function () {
				var elem = window.event.currentTarget;

				// no more than 2 call ajax
				if (elem.childElementCount >= 2) {
					return;
				}
				var text = elem.children[0].textContent;

				var fileInfo = text.substring(text.indexOf('('), text.length).replace('(', '').replace(')', '');
				var fileName = fileInfo.split('.')[0];
				var line = fileInfo.split(':')[1];

				var successCallback = function (aResponse) {
					var lineNumber = line;
					if (aResponse.length === 0) {
						var p = document.createElement('p');
						p.className = 'fileInfo';
						p.innerHTML = "Library files can not do code analysis.";
						elem.appendChild(p);
						$('#loading').css('display', 'none');
						return;
					}

					for (var i = 0; i < aResponse.length; i++) {
						var p = document.createElement('p');
						p.className = 'codestyle';

						if (lineNumber === aResponse[i].substring(0, aResponse[i].indexOf('&nbsp') - 1)) {
							p.style = 'color: red; font-weight: bold';
						} else {
							aResponse[i] = theme.apply(aResponse[i]);
						}
						if (!aResponse[i + 1]) {
							p.innerHTML = aResponse[i];
						} else {
							p.innerHTML = aResponse[i] + '</br>';
						}
						elem.appendChild(p);
					}
					$('#loading').css('display', 'none');
				}.bind(this);

				// parser.viewCode(fileName, line, 10, successCallback);
			};

			var elem = document.createElement('summary');
			elem.innerHTML = aStackTraces[i];

			if (isSourcePackage) {
				elem.style = 'color: #BBBB00';
			}

			details.appendChild(elem);
			rootElem.appendChild(details);
		}
	}
	*/

	drawLog(logs: string): void {
		var aLogs = logs.split('\r\t');
		var length = aLogs.length;

		var tbody = document.getElementById('logs').children[0];
		for (var i = 0; i < length; i++) {
			if (tbody.childElementCount >= clientConfig.removeNodeLimit) {
				tbody.removeChild(tbody.firstElementChild);
			}

			var re = /.+(.java|.class)(.*?)\)/g;
			var tr = document.createElement('tr');
			var td = document.createElement('td');

			tr.style = 'text-indent: 10px; !important';
			var text = aLogs[i];

			var span = document.createElement('span');
			if (text.indexOf('\\a\\r') > -1) {
				span.style = 'color: #BBBB00';
				span.innerHTML = text.substring('\\a\\r'.length, text.indexOf('</br>'));
				td.innerHTML = span.outerHTML + text.substring(text.indexOf('</br>'), text.length);
			} else {
				var regexText = re.exec(text);
				if (!!regexText && !!regexText[0]) {
					span.style = 'color: beige';
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
		var divTable = document.getElementsByClassName('table-responsive')[i];
		divTable.scrollTop = divTable.scrollHeight;
	}

	drawUsage(oData: any): void {
		var cpu = oData.cpu;
		var memory = oData.memory;

		var usageElem = document.getElementById('usage');

		var i = 0, j = 0;
		var acrInterval = setInterval (function() {
			usageElem.innerHTML = i + '/' + j;
			if( i === cpu && j === memory) {
				clearInterval(acrInterval);
			}

			if (i === cpu) {
				i = cpu;
			} else {
				i++;
			}

			if (j === memory) {
				j = memory;
			} else {
				j++;
			}
		}, 15);
	}


	drawInfos(oData: any): void {
		var version = oData.version;
		var branch = oData.branch;

		var infosElem = document.getElementById('infos');
		infosElem.innerHTML = version + '<br>' + branch;
	}

	drawAccessLogs(data: any): void {
		var elem = document.getElementById('accessLogs');
		data.accessLogs.forEach(function (item) {
			if (elem.childElementCount >= clientConfig.removeNodeLimit) {
				elem.removeChild(elem.firstElementChild);
			}
			var tr = document.createElement('tr');
			var td = document.createElement('td');
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
		var ctx = document.getElementById("canvas").getContext("2d");
		// window.chart = new Chart(ctx, chartConfig);
	}

	drawSetting(data: any): void {
		var table = document.getElementById('settingTable');
		for (var i in data) {
			var tr = document.createElement('tr');
			var key = document.createElement('td');
			var value = document.createElement('input');
			value.className = 'setting_values';
			value.value = data[i];
			for (var j = 0; j < clientConfig.readOnlyKeys.length; j++) {
				if (i === clientConfig.readOnlyKeys[j]) {
					value = document.createElement('td');
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
