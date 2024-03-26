import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthLoginInfo} from "../auth/login-info";
import {Observable} from "rxjs";
import {JwtResponse} from "../auth/jwt-response";
import {SignupInfo} from "../auth/signup-info";
import {StandardResponse} from "../StandartResponse";

const httpOptions = {
  headers: new HttpHeaders({'content-type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8080/signin';
  private signupUrl = 'http://localhost:8080/signup';

  constructor(private http: HttpClient) {}

  attemptAuth(credentials: AuthLoginInfo): Observable<StandardResponse<JwtResponse>> {
    return this.http.post<StandardResponse<JwtResponse>>(this.loginUrl, credentials, httpOptions);
  }

  signUp(info: SignupInfo): Observable<StandardResponse<string>> {
    return this.http.post<StandardResponse<string>>(this.signupUrl, info, httpOptions);
  }
}
