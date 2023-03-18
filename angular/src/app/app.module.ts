import { NgModule } from '@angular/core';
import { FormsModule}  from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';


import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { AppRoutingModule } from './app-routing.module';


import { HttpClientModule } from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';
import { ModComponent } from './mod/mod.component';
import { AdminComponent } from './admin/admin.component';
import { httpInterceptorProviders } from "./auth/auth-interceptor";
import { SideBarComponent } from './side-bar/side-bar.component';
import { FindComponent } from './find/find.component';
import { ProfileComponent } from './profile/profile.component';
import { ChatComponent } from './chat/chat.component';
import { AllChatComponent } from './all-chats/all-chat.component';



@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    UserComponent,
    ModComponent,
    AdminComponent,
    SideBarComponent,
    FindComponent,
    ProfileComponent,
    ChatComponent,
    AllChatComponent
  ],
    imports: [
      BrowserModule,
      AppRoutingModule,
      FormsModule,
      HttpClientModule

    ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
