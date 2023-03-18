export class User {
  constructor(public firstName: string,
              public lastName: string,
              public date: string ,
              public username: string ,
              public roles: string[]
              ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = date;
    this.username = username;
    this.roles = roles;
  }
}
