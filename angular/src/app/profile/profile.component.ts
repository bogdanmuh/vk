import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProfileService} from "./profile.service";
import {TokenStorageService} from "../auth/token-storage.service";
import {AddFriendsRequest} from "./addFriendsRequest";

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
  isOnline: boolean = false
  date: any
  roles: string[] = []
  friends: string[] = []
  selectedFile: File | undefined;
  retrievedImage: any;
  base64Data: any;
  retrieveResponse: any;
  message: string = "";
  errorMessage: string = "";

  imageName: any;
  public isLoggedIn: boolean = this.storageService.getLogIn();
  public isError: boolean = false;

  isCurrentUser: boolean;

  constructor(private activateRoute: ActivatedRoute,
              private profileService: ProfileService,
              private storageService : TokenStorageService,
              private router: Router) {
    this.id = activateRoute.snapshot.params['username'];
    this.isCurrentUser = this.a(this.id);
  }

  ngOnInit(): void {
    this.profileService.getUser(this.id).subscribe(response => {
      this.username = response.data.username;
      this.roles = response.data.roles;
      this.firstName = response.data.firstName;
      this.lastName = response.data.lastName;
      this.date = response.data.date;
      this.friends = response.data.friends;
      this.retrieveResponse = response.data.imageModel;
      this.isOnline = response.data.online;
      this.base64Data = this.retrieveResponse.picByte;
      this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;

      },response => {
        console.log(response);
        alert(response.error["message"]);
        this.isError = true;
        this.errorMessage = response.error["message"];
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
          this.retrieveResponse = res;
          this.base64Data = this.retrieveResponse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }

  a(username: String) : boolean {
    return this.storageService.getUsername() == username;
  }
  addFriends() {
    let addFriendsRequest = new AddFriendsRequest(this.storageService.getUsername(), this.username);
    this.profileService.addFriend(addFriendsRequest)
      .subscribe(
        res => {
          this.retrieveResponse = res;
          this.base64Data = this.retrieveResponse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }

  sendMessage() {
    this.router.navigate(['/chat/' + this.username] ,
      { queryParams: {
          chatId: -1 ,
          firstNameCompanion: this.firstName ,
          lastNameCompanion: this.lastName}
      });
  }





}
