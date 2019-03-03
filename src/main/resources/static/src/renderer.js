/**
 * Created by son on 2019-03-04.
 */
var renderer = {
    drawErrorElem: function (oResult) {
        var aExceptions = oResult.exceptions;
        var aStackTraces = oResult.stackTraces;

        var rootElem = document.getElementById('error');

        for (var i = 0; i < aExceptions.length; i++) {
            var elem = document.createElement('div');
            elem.innerHTML = aExceptions[i];
            elem.className = 'errorItem';

            var number = '' + i;
            elem.setAttribute('number', number);

            elem.onclick  = function() {
                this.drawStackTrace(window.event.currentTarget.getAttribute('number'));
            }.bind(this);

            sessionStorage.setItem(number, aStackTraces[i]);
            rootElem.appendChild(elem);
        }
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
                    aResponse.forEach(function (item) {
                        var p = document.createElement('p');
                        p.className = 'fileInfo';
                        p.innerHTML = item;
                        elem.appendChild(p);
                    });
                };

                parser.viewCode(fileName, line, 10, successCallback);
            };

            var elem = document.createElement('summary');
            elem.innerHTML = aStackTraces[i];
            details.appendChild(elem);
            rootElem.appendChild(details);
        }
    }
};
