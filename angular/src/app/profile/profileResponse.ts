export class ProfileResponse {
  constructor(public firstName: string,
              public lastName: string,
              public date: any,
              public username: string,
              public email: string,
              public imageModel: any,
              public roles: string[]) {

    this.imageModel = imageModel;
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = date;
    this.email = email;
    this.username = username;
    this.roles = roles;
  }
}
