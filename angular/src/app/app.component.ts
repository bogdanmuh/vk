import {Component, OnInit} from '@angular/core';

import {TokenStorageService} from './auth/token-storage.service';
import {Router} from "@angular/router";
import {FindComponent} from "./find/find.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  roles!: string[];
  authority!: string;
  text!: string;
  logIn = false;
  public password: string = "";
  title: string = "";
  constructor(private tokenStorage: TokenStorageService,
              private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.logIn = true;
      this.roles = this.tokenStorage.getAuthorities();
      this.roles.every(role => {
        if (role === 'ROLE_ADMIN') {
          this.authority = 'admin';
          return false;
        } else if (role === 'ROLE_PM') {
          this.authority = 'pm';
          return false;
        }
        this.authority = 'user';
        return true;
      });
    }
  }

  findUsers(){
    console.log("find users"+ this.text);
    this.router.navigate(['/find']);

  }
  logout() {
    this.tokenStorage.signOut();
    window.location.reload();

  }
}

