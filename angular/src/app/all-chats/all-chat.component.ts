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

  constructor(private allChatService : AllChatService,
              private storageService : TokenStorageService) { }

  ngOnInit(): void {
    this.getCompanions()
  }
  getCompanions(): void {
    this.allChatService.getCompanions().subscribe(
      data => {
        this.users = data;
        alert("success")
      },error=>alert("unsuccessful"))
  }


  diffGetTime(date: any): Date {
    let d = new Date(Date.now() - date);
    console.log(new Date(Date.now() - date.getTime()))
    return d;
  }
}
