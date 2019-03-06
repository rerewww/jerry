/**
 * Created by son on 2019-03-04.
 */
var renderer = {
    drawErrorElem: function (oResult) {
        var aExceptions = oResult.exceptions;
        var aStackTraces = oResult.stackTraces;

        var rootElem = document.getElementById('error');

        for (var i = 0; i < aExceptions.length; i++) {
            var tr = document.createElement('tr');
            var td = document.createElement('td');
            td.innerHTML = aExceptions[i];
            td.className = 'errorItem';

            var number = '' + i;
            td.setAttribute('number', number);

            td.onclick  = function() {
                this.drawStackTrace(window.event.currentTarget.getAttribute('number'));
            }.bind(this);

            sessionStorage.setItem(number, aStackTraces[i]);
            tr.appendChild(td);
            rootElem.appendChild(tr);
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
            details.onclick = function () {
                var elem = window.event.currentTarget;
                if (elem.childElementCount > 2) {
                    return;
                }
                var text = elem.children[0].textContent;

                var fileInfo = text.substring(text.indexOf('('), text.length).replace('(', '').replace(')', '');
                var fileName = fileInfo.split('.')[0];
                var line = fileInfo.split(':')[1];

                var successCallback = function (aResponse) {
                    if (aResponse.length === 0) {
                        var p = document.createElement('p');
                        p.className = 'fileInfo';
                        p.innerHTML = "not exist file";
                        elem.appendChild(p);
                        return;
                    }

                    var contents = '';
                    aResponse.forEach(function (item) {
                        contents += item + '\n';
                    });

                    var textarea = document.createElement('textarea');
                    textarea.id = 'codemirrorArea';
                    elem.appendChild(textarea);

                    var editor = CodeMirror.fromTextArea(textarea, {
                        lineNumbers: true,
                        lineWrapping: true,
                        theme: "darcula",
                        mode: "text/x-java",
                        styleActiveLine: true,
                        matchBrackets: true
                    });
                    editor.setValue(contents);
                    editor.setSize(null, 300);
                };

                parser.viewCode(fileName, line, 10, successCallback);
            };

            var elem = document.createElement('summary');
            elem.innerHTML = aStackTraces[i];
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
