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
    }
};