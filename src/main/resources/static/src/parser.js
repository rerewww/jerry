var parser = {
    start: function() {
        $.ajax({
            url: '/read.son',
            type:'GET',
            async: true,
            dataType: 'json',
            success: function(response) {
                if (response === null || response === undefined) {
                    return;
                }

                console.log(response);
            }
        })
    },

    viewCode: function (fileName, line, range, successCallback) {
        if (!line) {
            return;
        }
        $.ajax({
            url: '/viewCode.son',
            type:'GET',
            async: true,
            data: {
                fileName: fileName,
                line: line,
                range: range
            },
            dataType: 'json',
            success: successCallback
        })
    },

    accesssLogs: function () {
        var status =
        $.ajax({
            url: 'accessLogs.son',
            type: 'GET',
            async: true,
            dataType: 'json',
            success: function(response) {
                if (!response || !response.accessLogs || response.accessLogs.length === 0) {
                    return;
                }

                var date = moment().add(0, 'd').format();
                var successCnt = 0;
                var failCnt = 0;
                response.accessLogs.forEach(function (item) {
                    var aData = item.split(' ');
                    var length = aData.length;
                    for (var i = 0; i < length; i++) {
                        var code = Number(aData.pop());

                        if (!!code && (code >= 200 && code <= 308)) {
                            successCnt++;
                            break;
                        }
                        if (!!code && (code >= 400 && code <= 599)) {
                            failCnt++;
                            break;
                        }
                    }
                });

                var oSuccess = {
                    x: date,
                    y: successCnt
                };
                var oFail = {
                    x: date,
                    y: failCnt
                };

                renderer.drawAccessLogs(response.accessLogs);
                renderer.updateChart(oSuccess, oFail);
            }
        })
    }
};

var parserError = {
    start: function () {
        $.ajax({
            url: 'parse.son',
            type: 'GET',
            async: true,
            dataType: 'json',
            success: function(response) {
                renderer.drawErrorElem(response.logModel);
            }
        })
    }
};
