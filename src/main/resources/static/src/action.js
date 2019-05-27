// /**
//  * Created by son on 2019-03-09.
//  */
// var action = {
// 	getInfos: function () {
// 		$.ajax({
// 			url: 'getInfos.son',
// 			type: 'GET',
// 			async: true,
// 			dataType: 'json',
// 			success: function(response) {
// 				renderer.drawInfos(response);
// 			}
// 		})
// 	},
//
// 	setDefaultEnvs: function (data) {
// 		for (var i in data) {
// 			window._info.defaultEnvs[i] = data[i];
// 		}
// 	},
//
// 	reset: function () {
// 		var data = window._info.defaultEnvs;
// 		var table = document.getElementById('settingTable');
// 		for (var i = 0; i < table.childElementCount; i++) {
// 			if (table.children[i].lastElementChild.nodeName === "TD") {
// 				continue;
// 			}
// 			table.children[i].lastElementChild.value = data[table.children[i].firstChild.textContent];
// 		}
// 	},
//
// 	apply: function () {
// 		var data = {};
// 		var table = document.getElementById('settingTable');
// 		for (var i = 0; i < table.childElementCount; i++) {
// 			if (table.children[i].lastElementChild.nodeName === "TD") {
// 				continue;
// 			}
// 			data[table.children[i].firstChild.textContent] = table.children[i].lastElementChild.value;
// 		}
// 		$.ajax({
// 			url: 'apply.son',
// 			type: 'POST',
// 			async: true,
// 			contentType: "application/json;charset=UTF-8",
// 			traditional: true,
// 			data: JSON.stringify(data),
// 			dataType: 'json',
// 			success: function(response) {
// 				// alert
// 			}
// 		})
// 	}
// };