import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {TokenStorageService} from "../auth/token-storage.service";
import { Observable} from "rxjs";
import {ProfileResponse} from "./profileResponse";
import {AddFriendsRequest} from "./addFriendsRequest";
import {StandardResponse} from "../StandartResponse";

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

  getUser(userId:string): Observable<StandardResponse<ProfileResponse>> {
    return this.http.get<StandardResponse<ProfileResponse>>(this.findPersonUrl + userId,  this.tokenStorage.getHttpOptions())
  }

  loadImage(selectedFile: File) {
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, this.tokenStorage.getUsername());
    return this.http.post(this.loadImageUrl, uploadImageData, {observe: 'response'});
  }

  getImage(){
    return this.http.get(this.getMainImageUrl  + "/" + this.tokenStorage.getUsername());
  }

  addFriend(addFriendsRequest : AddFriendsRequest){
    return this.http.post(this.addFriends, addFriendsRequest, this.tokenStorage.getHttpOptions());
  }


}
