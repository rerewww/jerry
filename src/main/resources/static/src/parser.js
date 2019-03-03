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
    }
};
