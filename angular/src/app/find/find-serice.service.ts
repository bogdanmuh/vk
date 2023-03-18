import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {FindResponse} from "./findResponse";
import {FindRequest} from "./findRequest";


@Injectable({
  providedIn: 'root'
})
export class FindSericeService {

  private findAllUrl = 'http://localhost:8080/find';
  private findPersonUrl = 'http://localhost:8080/find';

  constructor(private http: HttpClient) {}
  findInfo(credentials: FindRequest ,token: string): Observable<FindResponse> {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `Bearer ${token}`,
      }),
    };
    console.log(credentials.text)
    return this.http.post<FindResponse>(this.findAllUrl, credentials, httpOptions);
  }
  findPerson(credentials: FindRequest ,token: string): Observable<FindResponse> {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `Bearer ${token}`,
      }),
    };
    console.log(credentials.text)
    return this.http.post<FindResponse>(this.findPersonUrl, credentials, httpOptions);
  }
}
