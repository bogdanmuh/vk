import {Component, OnInit} from '@angular/core';
import {User} from "./User";
import {AllChatService} from "./all-chat.service";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-all-chat',
  templateUrl: './all-chat.component.html',
  styleUrls: ['./all-chat.component.css']
})
export class AllChatComponent  implements OnInit {
  public message: string = "";
  public users: User[] = [];
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
        this.users = response["data"];
      },response => {
        console.log(response);
        alert(response.error["message"]);
        this.isError = true;
        this.errorMessage = response.error["message"];
      })
  }


  diffGetTime(date: any): Date {
    let d = new Date(Date.now() - date);
    console.log(new Date(Date.now() - date.getTime()))
    return d;
  }
}
