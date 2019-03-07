/**
 * Created by son on 2019-03-05.
 */
function connect(){
    sock = new SockJS("http://localhost:8888/stomp");
    stompClient = Stomp.over(sock);
    stompClient.connect({}, function(frame){
        console.log('connected stomp over sockjs');
        stompClient.subscribe('/subscribe/notice', onMessage);
        stompClient.debug = null;
    });

}

function onMessage(message){
    if (message.body === "") {
        return;
    }
    renderer.drawLog(message.body);
}