import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MessageRequest} from "./MesssageRequest";
import {TokenStorageService} from "../auth/token-storage.service";
import {ChatingService} from "./chating.service";
import {MessageResponse} from "./MessageResponse";
import {WebSocketService} from "../services/web-socket.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  public message: string = "";
  public usernameCompains: string;
  public messages: MessageResponse[] = [];
  public lastDate: Date = new Date();
  public isLoggedIn: boolean = this.tokenStorage.getLogIn();
  public webSocket: any;

  constructor(private actiateRoute: ActivatedRoute,
              private tokenStorage: TokenStorageService,
              private chatingService: ChatingService) {
    this.webSocket = new WebSocketService(tokenStorage, this);
    this.usernameCompains = actiateRoute.snapshot.params['username'];
    console.log("конструктор");
    this.chatingService.getFirstMessages(this.tokenStorage.getUsername(), this.usernameCompains, this.tokenStorage.getBearerToken())
      .subscribe(
        data => {
          console.log(data);
          this.messages = data;
        },
      )
    console.log("конструктор" + this.lastDate);
  }

  ngOnInit(): void {}

  sendMessage() {
    let messageRequest =
      new MessageRequest(this.tokenStorage.getUsername(), this.usernameCompains,this.message)
    console.log("отправляем сообщение" + messageRequest);
    this.webSocket.send(messageRequest);
    let messageResponse = new MessageResponse(messageRequest.message,messageRequest.from,messageRequest.date,messageRequest.to)
    this.addNewMessage(messageResponse);
  }
  addNewMessage(message : MessageResponse){
    this.messages.push(message);
  }




}
