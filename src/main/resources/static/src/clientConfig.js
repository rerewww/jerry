"use strict";
/**
 * Created by son on 2019-03-13.
 */
var jquery_1 = require('jquery');
var clientConfig = (function () {
    function clientConfig() {
        this.removeNodeLimit = 200;
        this.chartDataLimit = 6;
        this.readOnlyKeys = ['serverPort', 'ajpProtocol', 'ajpPort', 'ajpEnabled'];
    }
    clientConfig.checkedLib = function () {
        return jquery_1["default"]('input[id=libCheck]').is(':checked');
    };
    clientConfig.checkedLog = function () {
        return jquery_1["default"]('input[id=logCheck]').is(':checked');
    };
    clientConfig.checkedError = function () {
        return jquery_1["default"]('input[id=errorCheck]').is(':checked');
    };
    clientConfig.checkedAccess = function () {
        return jquery_1["default"]('input[id=accessCheck]').is(':checked');
    };
    return clientConfig;
}());
exports.clientConfig = clientConfig;
//# sourceMappingURL=clientConfig.js.map