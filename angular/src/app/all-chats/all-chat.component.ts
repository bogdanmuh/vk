import {Component, OnInit} from '@angular/core';
import {LastMessage} from "./LastMessage";
import {AllChatService} from "./all-chat.service";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-all-chat',
  templateUrl: './all-chat.component.html',
  styleUrls: ['./all-chat.component.css']
})
export class AllChatComponent  implements OnInit {
  public message: string = "";
  public lastMessages: LastMessage[] = [];
  isLoggedIn: boolean = this.storageService.getLogIn();
  isError: boolean = false;
  errorMessage: string = "";

  constructor(private allChatService : AllChatService,
              private storageService : TokenStorageService) { }

  ngOnInit(): void {
    this.getCompanions()
  }
  getCompanions(): void {
    this.allChatService.getCompanions().subscribe(
      response => {
        console.log(response)
        this.lastMessages = response["data"];
      },response => {
        console.log(response);
        alert(response.error["message"]);
        this.isError = true;
        this.errorMessage = response.error["message"];
      })
  }

  getName(chatName: string, firstName: string, lastName: string): string {
    if (chatName != "") {
      return chatName;
    }
    return firstName + " " + lastName;
  }


  diffGetTime(date: any): Date {
    let d = new Date(Date.now() - date);
    console.log(new Date(Date.now() - date.getTime()))
    return d;
  }
}
