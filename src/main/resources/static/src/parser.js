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
