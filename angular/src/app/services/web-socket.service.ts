
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import {MessageResponse} from "../chat/MessageResponse";
import {TokenStorageService} from "../auth/token-storage.service";
import {ChatComponent} from "../chat/chat.component";

export class WebSocketService {

  webSocketEndPoint: string = 'http://localhost:8080/ws';
  topic: string;
  stompClient: any;

  constructor(private tokenStorage: TokenStorageService,
              private chatComponent: ChatComponent) {
    this.topic = "/user/" + tokenStorage.getUsername() + "/queue/messages";
    this.connect()
  }

  connect() {
    console.log("Initialize WebSocket Connection");
    const ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({
      'content-type': 'application/json',
      'Authorization': `Bearer ${this.tokenStorage.getToken()}`,
      'username': this.tokenStorage.getUsername()
    },
      () => {
        console.log("connected" + this.topic);
        that.stompClient.subscribe(this.topic, (message: any) => {
          console.log("Message Recieved from Server :: " + message);
          const notification = JSON.parse(message.body);
          switch (notification["event"]) {
            case 'message':
              this.addNewMessage(notification);
              break;
            default:
              console.log("default");
              break;
          }
        });
        this.stompClient.reconnect_delay = 2000;
      }, this.errorCallBack);

  };

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error: string) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  send(message: any) {
    console.log("calling logout api via web socket");
    this.stompClient.send("/app/topic", this.tokenStorage.getHttpOptions(), JSON.stringify(message));
  }

  addNewMessage(message: MessageResponse) {
    console.log("add message :: " + message.recipient);
    this.chatComponent.addNewMessage(message);
  }
}
