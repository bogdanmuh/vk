import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProfileService} from "./profile.service";
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  id: string
  username: string = ""
  firstName: string = ""
  lastName: string = ""
  date: any
  roles: string[] = []
  public isLoggedIn: boolean = this.storageService.getLogIn();
  constructor(private actiateRoute: ActivatedRoute,
              private profileService: ProfileService,
              private storageService : TokenStorageService) {
    this.id = actiateRoute.snapshot.params['username'];
  }

  ngOnInit(): void {
    this.profileService.getUser(this.id).subscribe(data=>{

      this.username = data.username;
      this.roles = data.roles;
      this.firstName = data.firstName;
      this.lastName = data.lastName;
      this.date = data.date;

      alert("success")
      }
    )
  }
}
