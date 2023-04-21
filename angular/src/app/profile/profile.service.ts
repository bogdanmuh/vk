import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {TokenStorageService} from "../auth/token-storage.service";
import {Observable} from "rxjs";
import {ProfileResponse} from "./profileResponse";
import {AddFriendsRequest} from "./addFriendsRequest";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private findPersonUrl = 'http://localhost:8080/profile/';
  private loadImageUrl = 'http://localhost:8080/profile/upload';
  private getMainImageUrl = 'http://localhost:8080/profile/image';

  private addFriends = 'http://localhost:8080/profile/friend';


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

  loadImage(selectedFile: File) {
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `Bearer ${this.tokenStorage.getBearerToken()}`
      }),
    };
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, this.tokenStorage.getUsername());

    return this.http.post(this.loadImageUrl, uploadImageData, {observe: 'response'});
  }

  getImage(){
    return this.http.get(this.getMainImageUrl  + "/" + this.tokenStorage.getUsername());
  }

  addFriend(addFriendsRequest : AddFriendsRequest){
    const httpOptions = {
      headers: new HttpHeaders({
        'content-type': 'application/json',
        'Authorization': `Bearer ${this.tokenStorage.getBearerToken()}`
      }),
    };
    return this.http.post(this.addFriends, addFriendsRequest, httpOptions);
  }


}
