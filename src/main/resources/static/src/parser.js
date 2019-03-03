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
