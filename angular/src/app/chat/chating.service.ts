import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageRequest} from "./MesssageRequest";
import {Message} from "./Message";
import {NewChatRequest} from "./NewChatRequest";
import {TokenStorageService} from "../auth/token-storage.service";
import {StandardResponse} from "../StandartResponse";
import {Response} from "./Response";
import {Chat} from "./Chat";

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

  findChat(newChatRequest: NewChatRequest) : Observable<StandardResponse<Chat>> {
    console.log(this.newChatUrl)
    return this.http.post <StandardResponse<Chat>> (
      this.newChatUrl,
      newChatRequest,
      this.tokenStorage.getHttpOptions());
  }

  getFirstMessages(chatId: number) : Observable<StandardResponse<Response>>  {
    console.log(this.chatingUrl + "?id=" + chatId + "&sender=" + this.tokenStorage.getUsername())
    return this.http.get<StandardResponse<Response>>(
      this.chatingUrl + "?id=" + chatId + "&sender=" + this.tokenStorage.getUsername(),
      this.tokenStorage.getHttpOptions());
  }

  getFirstMessage(companion: String) : Observable<StandardResponse<Response>>  {
    console.log(this.chatingUrl + "?companion=" + companion + "&sender=" + this.tokenStorage.getUsername())
    return this.http.get<StandardResponse<Response>>(
      this.chatingUrl + "?companion=" + companion + "&sender=" + this.tokenStorage.getUsername(),
      this.tokenStorage.getHttpOptions());
  }

}
