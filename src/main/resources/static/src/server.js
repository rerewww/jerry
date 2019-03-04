/**
 * Created by son on 2019-03-05.
 */
function connect(){
    sock = new SockJS("http://localhost:8888/stomp");
    stompClient = Stomp.over(sock);
    stompClient.connect({}, function(frame){
        console.log('connected stomp over sockjs');
        stompClient.subscribe('/subscribe/notice', onMessage);
    });

}

function onMessage(message){
    console.log("Recevied server msg: "+message);
}
