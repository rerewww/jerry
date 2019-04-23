/**
 * Created by son on 2019-03-09.
 */
var action = {
    getUsage: function () {
        $.ajax({
            url: 'usage.son',
            type: 'GET',
            async: true,
            dataType: 'json',
            success: function(response) {
                renderer.drawUsage(response);
            }
        })
    },
    getInfos: function () {
        $.ajax({
            url: 'getInfos.son',
            type: 'GET',
            async: true,
            dataType: 'json',
            success: function(response) {
                renderer.drawInfos(response);
            }
        })
    },

    setDefaultEnvs: function (data) {
        for (var i in data) {
            window._info.defaultEnvs[i] = data[i];
        }
    },

    reset: function () {
        var data = window._info.defaultEnvs;
        var table = document.getElementById('settingTable');
        for (var i = 0; i < table.childElementCount; i++) {
            if (table.children[i].lastElementChild.nodeName === "TD") {
                continue;
            }
            table.children[i].lastElementChild.value = data[table.children[i].firstChild.textContent];
        }
    },

    apply: function () {
        $.ajax({
            url: 'apply.son',
            type: 'POST',
            async: true,
            dataType: 'json',
            success: function(response) {
                console.log(response);
            }
        })
    }
};