export class User {
  constructor(public id: number,
              public username: string,
              public firstName: string ,
              public lastName: string ,
  ) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
