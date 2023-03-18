import {Component, OnInit} from '@angular/core';
import {User} from "./User";
import {AllChatService} from "./all-chat.service";

@Component({
  selector: 'app-all-chat',
  templateUrl: './all-chat.component.html',
  styleUrls: ['./all-chat.component.css']
})
export class AllChatComponent  implements OnInit {
  public message: string = "";
  public users: User[] = [];

  constructor(private allChatService : AllChatService) { }

  ngOnInit(): void {
    this.getCompanions()
  }
  getCompanions(): void {
    this.allChatService.getCompanions().subscribe(
      data => {
        console.log(data);
        this.users = data;
        console.log(this.users);
        alert("success")
      },error=>alert("unsuccess"))
  }
}
