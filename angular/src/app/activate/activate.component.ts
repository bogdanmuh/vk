import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "../profile/profile.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ProfileResponse} from "../profile/profileResponse";
import {Observable} from "rxjs";
import {Message} from "./Message";

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent {

  code: String = ""
  message: String = ""
  constructor(private actiateRoute: ActivatedRoute,
              private http: HttpClient) {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json'
      }),
    };
    this.http.get <Message>(
      'http://localhost:8080/activate/' + actiateRoute.snapshot.params['code'],
      httpOptions).subscribe(data => {
      this.message = data.message;
    })

  }
}
