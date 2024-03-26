import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {TokenStorageService} from "../auth/token-storage.service";
import {User} from "./User";
import {StandardResponse} from "../StandartResponse";

@Injectable({
  providedIn: 'root'
})
export class AllChatService {
  private allChat = 'http://localhost:8080/allChat';
  constructor(private http: HttpClient,
              private tokenStorage : TokenStorageService) { }

  getCompanions(): Observable<StandardResponse<User[]>> {
    const params = new HttpParams()
      .set('user_id', this.tokenStorage.getId())
      .set('content-type','application/json')
      .set('Authorization',`${this.tokenStorage.getBearerToken()}`);

    return this.http.get<StandardResponse<User[]>>(this.allChat,{params});
  }
}
