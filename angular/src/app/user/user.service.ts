import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:8080/user';
  private modUrl = 'http://localhost:8080/mod';
  private adminUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) {}

  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, {responseType: 'text'});
  }

  getPMBoard(): Observable<string> {
    return this.http.get(this.modUrl, {responseType: 'text'});
  }

  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, {responseType: 'text'});
  }
}
