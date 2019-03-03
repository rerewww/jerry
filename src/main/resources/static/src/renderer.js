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
        var elem = document.getElementById('stack_trace');
        elem.innerHTML = sessionStorage.getItem(number);
    }
};
