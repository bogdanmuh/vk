import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MessageRequest} from "./MesssageRequest";
import {TokenStorageService} from "../auth/token-storage.service";
import {ChatingService} from "./chating.service";
import {MessageResponse} from "./MessageResponse";
import {WebSocketService} from "../services/web-socket.service";
import {NewChatRequest} from "./NewChatRequest";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  public message: string = "";
  public usernameCompains: string = "";
  public idCompains: string;
  public isCompainsOnline: boolean = false;
  public chatId: number = -1;
  public messages: MessageResponse[] = [];
  public lastDate: Date = new Date();
  public isLoggedIn: boolean = this.tokenStorage.getLogIn();

  public isNewChat: boolean = false;

  public last_and_first_Name: string = "";
  public webSocket: any;
//TODO перейти на айди вместо username
  constructor(private actiateRoute: ActivatedRoute,
              private tokenStorage: TokenStorageService,
              private chatingService: ChatingService) {
    this.webSocket = new WebSocketService(tokenStorage, this);
    actiateRoute.queryParams.subscribe(
      (queryParam: any) => {
        this.usernameCompains = queryParam['usernameCompains'];
        this.last_and_first_Name = queryParam['lastNameCompains'] + " " + queryParam['firstNameCompains'];
      }
    );
    this.idCompains = "-1"
    console.log(actiateRoute.snapshot.params['chat_id']);
    this.chatId = actiateRoute.snapshot.params['chat_id'];
    if (this.chatId  == -1) {
      this.chatingService
        .getFirstMessagess([this.tokenStorage.getUsername(), this.usernameCompains])
        .subscribe(
          (data: any)  => {
            console.log(data);
            this.messages = data["list"];
            this.isCompainsOnline = data["online"];
            this.changeChatId(<number> data["id"])
          },
        )
    } else {
      this.chatingService
        .getFirstMessages(this.chatId)
        .subscribe(
          (data: any)  => {
            console.log(data);
            this.messages = data["list"];
            this.isCompainsOnline = data["online"];
          },
        )
    }
  }

  ngOnInit(): void {}

  sendMessage() {
    let messageRequest = new MessageRequest(this.tokenStorage.getUsername(), this.usernameCompains, this.message, this.chatId)
    console.log("отправляем сообщение" + messageRequest);
    this.webSocket.send(messageRequest);
    let messageResponse = new MessageResponse(messageRequest.message, messageRequest.sender, messageRequest.date, messageRequest.recipient)
    this.addNewMessage(messageResponse);
  }

  sendNewMessage(){
    let newChatRequest = new NewChatRequest("",[this.tokenStorage.getUsername(), this.usernameCompains])
    console.log(newChatRequest);;
    this.chatingService.findChat(newChatRequest)
      .subscribe((data: any)  => {
        console.log("создали новый chat");
        this.sendMessage();
      });
  }

  addNewMessage(message : MessageResponse) {
    this.messages.push(message);
  }

  printOnline(user: string, online: boolean) : boolean {
    if (user == this.usernameCompains) {
      return online
    }
    return true;
  }

  changeChatId(id: number){
    this.isNewChat = id == -1;
    this.chatId = id;
  }

}
