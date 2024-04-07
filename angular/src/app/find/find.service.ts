import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {FindResponse} from "./findResponse";
import {FindRequest} from "./findRequest";
import {StandardResponse} from "../StandartResponse";
import {User} from "./User";


@Injectable({
  providedIn: 'root'
})
export class FindService {

  private findAllUrl = 'http://localhost:8080/find';
  private findPersonUrl = 'http://localhost:8080/find';

  constructor(private http: HttpClient) {}
  findInfo(credentials: FindRequest ,httpOptions:{ headers: HttpHeaders }): Observable<StandardResponse<User[]>> {
    console.log(credentials.text)
    return this.http.post<StandardResponse<User[]>>(this.findAllUrl, credentials, httpOptions);
  }
  findPerson(credentials: FindRequest ,httpOptions:{ headers: HttpHeaders }): Observable<StandardResponse<FindResponse>>  {
    console.log(credentials.text)
    return this.http.post<StandardResponse<FindResponse>>(this.findPersonUrl, credentials, httpOptions);
  }
}
