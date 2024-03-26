import { Component, OnInit } from '@angular/core';

import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthLoginInfo } from '../auth/login-info';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string = "";
  public password: string = "";
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];


  constructor(private authService: AuthService,
              private tokenStorage: TokenStorageService,
              private router: Router) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  onSubmit() {
    let loginInfo = new AuthLoginInfo(this.username, this.password);
    console.log(loginInfo);
    this.authService.attemptAuth(loginInfo).subscribe(response => {
        console.log(response);
        this.tokenStorage.saveToken(response.data.token);
        this.tokenStorage.saveUsername(response.data.username);
        this.tokenStorage.saveId(response.data.id);
        this.tokenStorage.saveAuthorities(response.data.roles);
        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.roles = this.tokenStorage.getAuthorities();
        this.router.navigate(['/profile/' + response.data.username])
      },
      response => {
        console.log(response);
        alert(response.error["message"]);
        this.isLoginFailed = true;
        this.errorMessage = response.error["message"];
      }
    );
  }
}
