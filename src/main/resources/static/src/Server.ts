import Stomp from "stompjs";
import SockJS from "sockjs-client";
import {ClientConfig} from "./ClientConfig";
import {Renderer} from "./Renderer";

/**
 * Created by son on 2019-03-05.
 */

const renderer: Renderer = new Renderer();
const clientConfig: ClientConfig = new ClientConfig();
export class Server {
	public connect(): void {
		const sock = new SockJS(window.location.origin + '/stomp');
		const stompClient = Stomp.over(sock);
		Stomp.over(stompClient.connect({}, function(){
			stompClient.subscribe('/subscribe/logs', onMessageForLogs);
			stompClient.subscribe('/subscribe/accessLogs', onMessageForAccessLogs);
			stompClient.subscribe('/subscribe/errorLogs', onMessageForErrorLogs);
			stompClient.subscribe('/subscribe/usage', onMessageForUsage);
			stompClient.debug = null;
		}));
	}
}

function onMessageForLogs(message): void {
	if (message.body === "" || !clientConfig.checkedLog()) {
		return;
	}
	renderer.renderLogs(message.body);
}

function onMessageForAccessLogs(message): void {
	if (message.body === "" || !clientConfig.checkedAccess()) {
		return;
	}

	var oData = JSON.parse(message.body);
	renderer.drawAccessLogs(oData);
}

function onMessageForErrorLogs(message): void {
	if (message.body === "" || !clientConfig.checkedError()) {
		return;
	}
	renderer.renderErrorLogs(JSON.parse(message.body));
}

function onMessageForUsage(message): void {
	if (message.body === "") {
		return;
	}
	renderer.renderUsage(JSON.parse(message.body));
}