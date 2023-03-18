export class ProfileResponse {
  constructor(public firstName: string,
              public lastName: string,
              public date: any,
              public username: string,
              public email: string,
              public roles: string[]) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = date;
    this.email = email;
    this.username = username;
    this.roles = roles;
  }
}
