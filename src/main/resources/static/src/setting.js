/**
 * Created by son on 2019-03-07.
 */
var setting = {
    onToggleLibEvent: function () {
        var isCheckedLib= $('input[id=libCheck]').is(':checked');
        if (!isCheckedLib) {
            $('input[id=libCheck]').trigger('click');
        }
    },
    onToggleLogEvent: function () {
        var isCheckedLog = $('input[id=logCheck]').is(':checked');
        if (!isCheckedLog) {
            $('input[id=logCheck]').trigger('click');
        }
    },
    onToggleErrorEvent: function () {
        var isCheckedError = $('input[id=errorCheck]').is(':checked');
        if (!isCheckedError) {
            $('input[id=errorCheck]').trigger('click');
        }
    },
    onToggleAccessEvent: function () {
        var isCheckedAccess= $('input[id=accessCheck]').is(':checked');
        if (!isCheckedAccess) {
            $('input[id=accessCheck]').trigger('click');
        }
    },
    dropDowns: function () {
        document.getElementById("options").classList.toggle("show");
    }
};

window.onclick = function(e) {
    if (!e.target.matches('.dropbtn')) {
        var options = document.getElementById("options");
        if (!e.target.matches('.slider') && !e.target.matches('input') && options.classList.contains('show')) {
            options.classList.remove('show');
        }
    }

    var stackTraces = document.getElementById('stack_trace');

    if (e.target.matches('.errorItem') || e.target.matches('summary') || e.target.matches('.codestyle')) {
        return;
    }

    if (!!stackTraces && stackTraces.firstElementChild !== null && stackTraces.firstElementChild.tagName === 'DETAILS') {
        while (stackTraces.childElementCount > 0) {
            stackTraces.removeChild(stackTraces.firstElementChild)
        }

        var header = document.createElement('h4');
        header.innerHTML = '<h4 class="container_headers header-title mt-0">Please select error log in table</h4>';
        stackTraces.appendChild(header);
    }
};