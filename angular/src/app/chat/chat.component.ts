import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MessageRequest} from "./MesssageRequest";
import {TokenStorageService} from "../auth/token-storage.service";
import {ChatingService} from "./chating.service";
import {MessageResponse} from "./MessageResponse";

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

  constructor(private actiateRoute: ActivatedRoute,
              private tokenStorage: TokenStorageService,
              private chatingService: ChatingService) {
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
    setInterval(()=>{

      /*let messageRequest =
        new MessageRequest(this.tokenStorage.getUsername(), this.usernameCompains)

      messageRequest.date = this.lastDate;
      this.chatingService.updatechat(messageRequest, this.tokenStorage.getBearerToken())
        .subscribe(
          data => {
            console.log(data);

          },
        )*/
    }, 1000 );
  }

  ngOnInit(): void {}

  refresh(): void {
    window.location.reload();
  }
  isOurAccout(username: string): boolean {
    return username == this.tokenStorage.getUsername();
  }
  sendMessage() {
    let messageRequest =
      new MessageRequest(this.tokenStorage.getUsername(), this.usernameCompains)
    messageRequest.message = this.message;
    console.log("отправляем сообщение" + messageRequest);
    this.chatingService.chating(messageRequest, this.tokenStorage.getBearerToken())
      .subscribe(
        error=>{
          alert("unsuccess")
        })
  }

}
