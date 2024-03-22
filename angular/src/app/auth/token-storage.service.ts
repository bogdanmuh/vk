import {Injectable} from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const ID_KEY = 'AuthId';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private roles: Array<string> = [];
  private isLogIn: boolean = false;


  signOut() {
    localStorage.clear();
  }

  public saveToken(token: string) {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.setItem(TOKEN_KEY, token);
    this.isLogIn = true;
  }
  public getLogIn() : boolean {
   return this.getToken() != null;
  }
  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY)!;
  }
  public getBearerToken(): string {
    return "Bearer " + localStorage.getItem(TOKEN_KEY)!;
  }

  public saveId(id: number) {
    localStorage.removeItem(ID_KEY);
    localStorage.setItem(ID_KEY, id.toString());
  }

  public getId() {
    return localStorage.getItem(ID_KEY)!;
  }

  public saveUsername(username: string) {
    localStorage.removeItem(USERNAME_KEY);
    localStorage.setItem(USERNAME_KEY, username);
  }

  public getUsername(): string {
    return localStorage.getItem(USERNAME_KEY)!;
  }

  public saveAuthorities(authorities: string[]) {
    localStorage.removeItem(AUTHORITIES_KEY);
    localStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    this.roles = [];
    if (localStorage.getItem(TOKEN_KEY)) {
      JSON.parse(localStorage.getItem(AUTHORITIES_KEY)!).forEach((authority: string) => {
        console.log('put role' + authority);
        this.roles.push(authority);
      });
    }
    return this.roles;
  }

  public getHttpOptions():{ headers: HttpHeaders } {
    return {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `Bearer ${this.getToken()}`
      }),
    };
  }


}
