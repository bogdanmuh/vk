import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {RegistrationComponent} from './registration/registration.component';
import {LoginComponent} from './login/login.component';
import {AdminComponent} from './admin/admin.component';
import {FindComponent} from "./find/find.component";
import {ProfileComponent} from "./profile/profile.component";
import {ChatComponent} from "./chat/chat.component";
import {AllChatComponent} from "./all-chats/all-chat.component";
import {ActivateComponent} from "./activate/activate.component";


const routes: Routes = [

  {
    path: 'admin',
    component: AdminComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'find',
    component: FindComponent
  },
  {
    path: 'signup',
    component: RegistrationComponent
  },
  {
    path: 'profile/:username',
    component: ProfileComponent
  },
  {
    path: 'activate/:code',
    component: ActivateComponent
  },
  {
    path: 'chat/:user_id',
    component: ChatComponent
  },
  {
    path: 'allchat',
    component: AllChatComponent
  },
  {
    path: '',
    redirectTo: 'allchat',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
