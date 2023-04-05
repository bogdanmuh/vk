import { Component } from '@angular/core';
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  public isLoggedIn: boolean = this.storageService.getLogIn();
  constructor(private storageService : TokenStorageService) { }
}
