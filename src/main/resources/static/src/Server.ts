import {clientConfig} from "./ClientConfig";
import {Renderer} from "./Renderer";
import {Stomp} from "@stomp/stompjs";
import SockJS from "sockjs";

/**
 * Created by son on 2019-03-05.
 */

export class Server {
    private sock;
    private stompClient;
    private renderer = new Renderer();

    public connect(): void {
        this.sock = SockJS.createServer({sockjs_url:window.location.origin + '/stomp'});
        this.stompClient = Stomp.over(this.sock);
        Stomp.over(this.stompClient.connect({}, function(){
            this.stompClient.subscribe('/subscribe/logs', this.onMessageForLogs);
            this.stompClient.subscribe('/subscribe/accessLogs', this.onMessageForAccessLogs);
            this.stompClient.subscribe('/subscribe/errorLogs', this.onMessageForErrorLogs);
            this.stompClient.subscribe('/subscribe/usage', this.onMessageForUsage);
            this.stompClient.debug = null;
        }));
    }

    public onMessageForLogs(message): void {
        if (message.body === "" || !clientConfig.checkedLog()) {
            return;
        }
        this.renderer.drawLog(message.body);
    }

    public onMessageForAccessLogs(message): void {
        if (message.body === "" || !clientConfig.checkedAccess()) {
            return;
        }

        var oData = JSON.parse(message.body);
        this.renderer.drawAccessLogs(oData);
        if (!!oData && oData.accessLogs.length === 0) {
            return;
        }

        // var date = moment().add(0, 'd').format();
        var date = 0;
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
        this.renderer.updateChart(oSuccess, oFail);
    }

    public onMessageForErrorLogs(message): void {
        if (message.body === "" || !clientConfig.checkedError()) {
            return;
        }
        this.renderer.drawErrorElem(JSON.parse(message.body));
    }

    public onMessageForUsage(message): void {
        if (message.body === "") {
            return;
        }
        this.renderer.drawUsage(JSON.parse(message.body));
    }
}