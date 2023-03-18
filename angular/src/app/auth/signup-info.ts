export class SignupInfo {
  role!: string[];
  constructor (public firstName: string,
               public lastName: string,
               public date: any,
               public username: string,
               public email: string,
               public password: string) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = date;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role= ['user'];
  }


}
