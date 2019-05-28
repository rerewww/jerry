// var parser = {
// 	start: function() {
// 		$.ajax({
// 			url: '/read.son',
// 			type:'GET',
// 			async: true,
// 			dataType: 'json',
// 			success: function(response) {
// 				if (response === null || response === undefined) {
// 					return;
// 				}
// 			}
// 		})
// 	},
//
// 	viewCode: function (fileName, line, range, successCallback) {
// 		if (!line) {
// 			return;
// 		}
//
// 		$('#loading').css('display', 'block');
// 		$.ajax({
// 			url: '/viewCode.son',
// 			type:'GET',
// 			async: true,
// 			data: {
// 				fileName: fileName,
// 				line: line,
// 				range: document.getElementById('codeRange').value
// 			},
// 			dataType: 'json',
// 			success: successCallback
// 		})
// 	}
// };
