import {Component, OnInit} from '@angular/core';
import {SignupInfo} from "../auth/signup-info";
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  public  firstName: string = "";
  public  lastName: string = "";
  public  date: any;
  public username: string = "";
  public password: string = "";
  public email: string = "";

  constructor(private authService: AuthService,
              private router: Router) { }

  userRegister() {
    let signupInfo = new SignupInfo(this.firstName, this.lastName,this.date, this.username, this.email,this.password)
    console.log("регистрация нового пользователя");
    this.authService.signUp(signupInfo).subscribe(
      data=>{
        console.log(data);
        alert(data + "Сссылка для активации отправлена. Проверьте почту")
        this.router.navigate(['/login' ])
      },
      error=>alert(error))
  }
  ngOnInit(): void {}
}
