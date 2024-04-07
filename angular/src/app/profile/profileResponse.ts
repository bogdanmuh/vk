export class ProfileResponse {
  constructor(public id: number,
              public firstName: string,
              public lastName: string,
              public date: any,
              public username: string,
              public email: string,
              public imageModel: any,
              public online: boolean,
              public roles: string[],
              public friends: string[]) {
    this.id = id
    this.imageModel = imageModel;
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = date;
    this.email = email;
    this.username = username;
    this.roles = roles;
    this.friends = friends;
    this.online = online;
  }
}
