import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {TokenStorageService} from "../auth/token-storage.service";
import {Observable} from "rxjs";
import {ProfileResponse} from "./profileResponse";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private findPersonUrl = 'http://localhost:8080/profile/';
  constructor(private http: HttpClient,
              private tokenStorage: TokenStorageService) {}

  getUser(userId:string): Observable<ProfileResponse> {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `Bearer ${this.tokenStorage.getBearerToken()}`,
      }),
    };
    console.log(userId)
    return this.http.get<ProfileResponse>(this.findPersonUrl + userId, httpOptions);

  }
}
