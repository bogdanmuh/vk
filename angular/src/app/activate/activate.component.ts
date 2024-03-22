import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent {

  code: String = ""
  message: String = ""
  constructor(private activateRoute: ActivatedRoute,
              private http: HttpClient) {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json'
      }),
    };
    this.http
      .get('http://localhost:8080/activate/' + activateRoute.snapshot.params['code'], httpOptions)
      .subscribe((data: any)  => {
      this.message = data["message"];
    })

  }
}
