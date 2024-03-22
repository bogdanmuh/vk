import { Component, OnInit } from '@angular/core';

import { AuthService } from '../auth/auth.service';
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
    this.authService.attemptAuth(loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveId(data.id);
        this.tokenStorage.saveAuthorities(data.roles);
        this.isLoggedIn = true;
        this.isLoginFailed = false;

        this.roles = this.tokenStorage.getAuthorities();
        this.router.navigate(['/profile/' + data.username])
      },
      error => {
        console.log(error);
        alert(error.error.message);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }
}
