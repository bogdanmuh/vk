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

  selectedFile: File | undefined;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string = "";
  imageName: any;

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

      this.retrieveResonse = data.imageModel;
      this.base64Data = this.retrieveResonse.picByte;
      this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;

      alert("success")
      }
    )
  }

  public onFileChanged(event: any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    console.log(this.selectedFile);
    if (this.selectedFile != null) {
      this.profileService.loadImage(this.selectedFile)
        .subscribe((response) => {
            if (response.status === 200) {
              this.message = 'Image uploaded successfully';
            } else {
              this.message = 'Image not uploaded successfully';
            }
          }
        );
    }
  }

  getImage() {
    this.profileService.getImage()
      .subscribe(
        res => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }




}
