import {Component} from '@angular/core';
import {TokenStorageService} from "../auth/token-storage.service";

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent {
  username: string = this.tokenStorage.getUsername();

  constructor(private tokenStorage: TokenStorageService) {
  }
}
