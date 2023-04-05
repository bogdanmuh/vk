export class User {
  constructor(public sender: string,
              public date: Date ,
              public message: string ,
              public firstName: string ,
              public lastName: string ,
              ) {
    this.sender = sender;
    this.date = date;
    this.message = message;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
