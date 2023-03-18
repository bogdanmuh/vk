import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {TokenStorageService} from "../auth/token-storage.service";
import {User} from "./User";

@Injectable({
  providedIn: 'root'
})
export class AllChatService {
  private allChat = 'http://localhost:8080/allChat';
  constructor(private http: HttpClient,
              private tokenStorage : TokenStorageService) { }

  getCompanions(): Observable<User[]> {
    const params = new HttpParams()
      .set('username', "num1.toString()")
      .set('username2', "num2.toString()")
      .set('content-type','application/json')
      .set('Authorization',`${this.tokenStorage.getBearerToken()}`);

    return this.http.get<User[]>(this.allChat,{params});
  }
}
