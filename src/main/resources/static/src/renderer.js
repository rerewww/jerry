/**
 * Created by son on 2019-03-04.
 */
var renderer = {
    drawErrorElem: function (oResult) {
        var aExceptions = oResult.exceptions;
        var aStackTraces = oResult.stackTraces;

        var rootElem = document.getElementById('error');

        var index = aExceptions.length - 1;
        var i = 0;
        while (i < 100) {
            var tr = document.createElement('tr');
            var td = document.createElement('td');
            td.innerHTML = aExceptions[index];
            td.className = 'errorItem';

            var number = '' + i;
            td.setAttribute('number', number);

            td.onclick  = function() {
                this.drawStackTrace(window.event.currentTarget.getAttribute('number'));
            }.bind(this);

            sessionStorage.setItem(number, aStackTraces[i]);
            tr.appendChild(td);
            rootElem.appendChild(tr);

            i++;
            index--;
        }
        this.autoScroll(0);
    },

    drawStackTrace: function (number) {
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

            if ($('input[id=libCheck]').is(':checked') && !isSourcePackage) {
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
                        p.innerHTML = "not exist file";
                        elem.appendChild(p);
                        return;
                    }

                    for (var i = 0; i < aResponse.length; i++) {
                        var p = document.createElement('p');
                        p.className = 'codestyle';

                        if (!aResponse[i + 1]) {
                            p.innerHTML = aResponse[i];
                        } else {
                            p.innerHTML = aResponse[i] + '</br>';
                        }

                        if (lineNumber === aResponse[i].substring(0, aResponse[i].indexOf('&nbsp') - 1)) {
                            p.style = 'color: red; font-weight: bold';
                        }
                        elem.appendChild(p);
                    }
                }.bind(this);

                parser.viewCode(fileName, line, 10, successCallback);
            };

            var elem = document.createElement('summary');
            elem.innerHTML = aStackTraces[i];

            if (isSourcePackage) {
                elem.style = 'background-color: steelblue';
            }

            details.appendChild(elem);
            rootElem.appendChild(details);
        }
    },

    drawLog: function (logs) {
        var aLogs = logs.split(',');
        var length = aLogs.length;

        var tbody = document.getElementById('logs').children[0];
        for (var i = 0; i < length; i++) {
            var tr = document.createElement('tr');
            var td = document.createElement('td');
            td.innerHTML = aLogs[i];

            tr.appendChild(td);
            tbody.appendChild(tr);
        }
        this.autoScroll(1);
    },

    autoScroll: function (i) {
        var divTable = document.getElementsByClassName('table-responsive')[i];
        divTable.scrollTop = divTable.scrollHeight;
    }
};
