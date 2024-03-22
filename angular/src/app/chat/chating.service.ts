import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageRequest} from "./MesssageRequest";
import {MessageResponse} from "./MessageResponse";
import {NewChatRequest} from "./NewChatRequest";
import {TokenStorageService} from "../auth/token-storage.service";

@Injectable({
  providedIn: 'root'
})
export class ChatingService {

  private chatingUrl = 'http://localhost:8080/chat';
  private updatechatUrl = 'http://localhost:8080/chat/update';

  private newChatUrl = 'http://localhost:8080/chat/new';

  constructor(private http: HttpClient,
             private tokenStorage: TokenStorageService) {}
  chating(credentials: MessageRequest ) {
    return this.http.post(
      this.chatingUrl,
      credentials,
      this.tokenStorage.getHttpOptions());
  }

  updatechat(messageRequest: MessageRequest, token: string): Observable<MessageResponse[]> {
    return this.http.post<MessageResponse[]>(
      this.updatechatUrl,
      messageRequest,
      this.tokenStorage.getHttpOptions());
  }

  findChat(newChatRequest: NewChatRequest){
    console.log(this.newChatUrl)
    return this.http.post(
      this.newChatUrl,
      newChatRequest,
      this.tokenStorage.getHttpOptions());
  }

  getFirstMessages(chatId: number){
    console.log(this.chatingUrl + + "?id=" + chatId)
    return this.http.get(
      this.chatingUrl + "?id=" + chatId,
      this.tokenStorage.getHttpOptions());
  }

  getFirstMessagess(usernames: String []){
    console.log(this.chatingUrl + + "?usernames=" + usernames)
    return this.http.get(
      this.chatingUrl + "?usernames=",
      this.tokenStorage.getHttpOptions());
  }


}
