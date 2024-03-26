import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MessageRequest} from "./MesssageRequest";
import {TokenStorageService} from "../auth/token-storage.service";
import {ChatingService} from "./chating.service";
import {Message} from "./Message";
import {WebSocketService} from "../services/web-socket.service";
import {NewChatRequest} from "./NewChatRequest";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  public message: string = "";
  public usernameCompanion: string = "";
  public idCompanion: string;
  public isCompanionOnline: boolean = false;
  public chatId: number = -1;
  public messages: Message[] = [];
  public lastDate: Date = new Date();
  public isLoggedIn: boolean = this.tokenStorage.getLogIn();

  public isNewChat: boolean = false;

  public last_and_first_Name: string = "";
  public webSocket: any;
//TODO перейти на айди вместо username
  constructor(private activateRoute: ActivatedRoute,
              private tokenStorage: TokenStorageService,
              private chatingService: ChatingService) {
    this.webSocket = new WebSocketService(tokenStorage, this);
    activateRoute.queryParams.subscribe(
      (queryParam: any) => {
        this.chatId = queryParam['chatId'];
        this.last_and_first_Name = queryParam['lastNameCompanion'] + " " + queryParam['firstNameCompanion'];
      }
    );
    this.idCompanion = "-1"
    console.log(activateRoute.snapshot.params['user_id']);
    this.usernameCompanion = activateRoute.snapshot.params['user_id'];
    if (this.chatId  == -1) {
      this.chatingService
        .getFirstMessage([this.tokenStorage.getUsername(), this.usernameCompanion])
        .subscribe(response => {
            console.log(response);
            this.messages = response.data.list;
            this.isCompanionOnline = response.data.online;
            this.changeChatId(response.data.chatId)
          }
        )
    } else {
      this.chatingService
        .getFirstMessages(this.chatId).subscribe(response  => {
            console.log(response);
          this.messages = response.data.list;
          this.isCompanionOnline = response.data.online;
          }
        )
    }
  }

  ngOnInit(): void {}

  sendMessage() {
    let messageRequest = new MessageRequest(this.tokenStorage.getUsername(), this.usernameCompanion, this.message, this.chatId)
    console.log("отправляем сообщение" + messageRequest);
    this.webSocket.send(messageRequest);
    let messageResponse = new Message(messageRequest.message, messageRequest.sender, messageRequest.date, messageRequest.recipient)
    this.addNewMessage(messageResponse);
  }

  sendNewMessage(){
    let newChatRequest = new NewChatRequest("",[this.tokenStorage.getUsername(), this.usernameCompanion])
    console.log(newChatRequest);
    this.chatingService.findChat(newChatRequest)
      .subscribe((response: any)  => {
        this.chatId = response["data.id"];
        console.log("создали новый chat");
        this.sendMessage();
      });
  }

  addNewMessage(message : Message) {
    this.messages.push(message);
  }

  printOnline(user: string, online: boolean) : boolean {
    if (user == this.usernameCompanion) {
      return online
    }
    return true;
  }

  changeChatId(id: number){
    this.isNewChat = id == -1;
    this.chatId = id;
  }

}
