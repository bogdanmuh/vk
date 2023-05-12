import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageRequest} from "./MesssageRequest";
import {MessageResponse} from "./MessageResponse";

@Injectable({
  providedIn: 'root'
})
export class ChatingService {

  private chatingUrl = 'http://localhost:8080/chat';
  private updatechatUrl = 'http://localhost:8080/chat/update';
  constructor(private http: HttpClient) {}
  chating(credentials: MessageRequest ,token: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `${token}`,
      }),
    };

    return this.http.post(this.chatingUrl, credentials, httpOptions);
  }

  updatechat(messageRequest: MessageRequest, token: string): Observable<MessageResponse[]> {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `${token}`,
      }),
    };
    return this.http.post<MessageResponse[]>(this.updatechatUrl, messageRequest, httpOptions);
  }

  getFirstMessages(from: string, to: string, token: string){
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `${token}`,
      }),
    };
    console.log(this.chatingUrl + "?from=" + from + "&to=" + to)
    return this.http.get(
      this.chatingUrl+"?from=" + from + "&to=" + to,
      httpOptions);
  }

}
