import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageRequest} from "./MesssageRequest";
import {Message} from "./Message";
import {NewChatRequest} from "./NewChatRequest";
import {TokenStorageService} from "../auth/token-storage.service";
import {StandardResponse} from "../StandartResponse";
import {Response} from "./Response";

@Injectable({
  providedIn: 'root'
})
export class ChatingService {

  private chatingUrl = 'http://localhost:8080/chat';
  private updateChatUrl = 'http://localhost:8080/chat/update';

  private newChatUrl = 'http://localhost:8080/chat/new';

  constructor(private http: HttpClient,
             private tokenStorage: TokenStorageService) {}

  chating(credentials: MessageRequest ) {
    return this.http.post(
      this.chatingUrl,
      credentials,
      this.tokenStorage.getHttpOptions());
  }

  updateChat(messageRequest: MessageRequest): Observable<Message[]> {
    return this.http.post<Message[]>(
      this.updateChatUrl,
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

  getFirstMessages(chatId: number) : Observable<StandardResponse<Response>>  {
    console.log(this.chatingUrl + + "?id=" + chatId)
    return this.http.get<StandardResponse<Response>>(
      this.chatingUrl + "?id=" + chatId,
      this.tokenStorage.getHttpOptions());
  }

  getFirstMessage(usernames: String []) : Observable<StandardResponse<Response>>  {
    console.log(this.chatingUrl + + "?usernames=" + usernames)
    return this.http.get<StandardResponse<Response>>(
      this.chatingUrl + "?usernames=",
      this.tokenStorage.getHttpOptions());
  }


}
