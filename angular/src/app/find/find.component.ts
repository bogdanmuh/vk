import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../auth/token-storage.service";
import {FindService} from "./find.service";
import {FindRequest} from "./findRequest";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.css']
})
export class FindComponent implements OnInit {
  text!: string;

  users: String[]=[];

  public isLoggedIn: boolean = this.tokenStorage.getLogIn();

  constructor(private tokenStorage: TokenStorageService,
              private f:AppComponent,

              private findServices: FindService) { }

  findUsers() {
    console.log("find users " + this.f.text, this.tokenStorage.getToken());
    let find = new FindRequest(this.f.text);
    this.findServices.findInfo(find, this.tokenStorage.getHttpOptions())
      .subscribe( response => {
        console.log(response);
        this.users = response.data.users;
      },error=>{
        console.log(error)
        alert("unsuccess")

      });
  }

  ngOnInit(): void {
    this.findUsers()
  }
}
