"use strict";
var clientConfig_1 = require("./clientConfig");
/**
 * Created by son on 2019-03-05.
 */
var Server = (function () {
    function Server() {
    }
    Server.prototype.connect = function () {
        sock = new SockJS(window.location.origin + '/stomp');
        stompClient = Stomp.over(sock);
        Stomp.over(stompClient.connect({}, function () {
            stompClient.subscribe('/subscribe/logs', onMessageForLogs);
            stompClient.subscribe('/subscribe/accessLogs', onMessageForAccessLogs);
            stompClient.subscribe('/subscribe/errorLogs', onMessageForErrorLogs);
            stompClient.subscribe('/subscribe/usage', onMessageForUsage);
            stompClient.debug = null;
        }));
    };
    Server.prototype.onMessageForLogs = function (message) {
        if (message.body === "" || !clientConfig_1.clientConfig.checkedLog()) {
            return;
        }
        renderer.drawLog(message.body);
    };
    Server.prototype.onMessageForAccessLogs = function (message) {
        if (message.body === "" || !clientConfig_1.clientConfig.checkedAccess()) {
            return;
        }
        var oData = JSON.parse(message.body);
        renderer.drawAccessLogs(oData);
        if (!!oData && oData.accessLogs.length === 0) {
            return;
        }
        var date = moment().add(0, 'd').format();
        var successCnt = 0;
        var failCnt = 0;
        oData.accessLogs.forEach(function (item) {
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
        renderer.updateChart(oSuccess, oFail);
    };
    Server.prototype.onMessageForErrorLogs = function (message) {
        if (message.body === "" || !clientConfig_1.clientConfig.checkedError()) {
            return;
        }
        renderer.drawErrorElem(JSON.parse(message.body));
    };
    Server.prototype.onMessageForUsage = function (message) {
        if (message.body === "") {
            return;
        }
        renderer.drawUsage(JSON.parse(message.body));
    };
    return Server;
}());
exports.Server = Server;
//# sourceMappingURL=server.js.map