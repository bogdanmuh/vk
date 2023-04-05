import { Component } from '@angular/core';
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent {
  public isLoggedIn: boolean = this.storageService.getLogIn();
  constructor(private storageService : TokenStorageService) { }
}
