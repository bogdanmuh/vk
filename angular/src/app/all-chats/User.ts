export class User {
  constructor(public username: string,
              public date: Date ,
              public message: string ,
              public firstName: string ,
              public lastName: string ,
              public chat_id: string,
              public usernameCompains: string ,
              public firstNameCompains: string ,
              public lastNameCompains: string ,
              ) {
    this.username = username;
    this.date = date;
    this.message = message;
    this.firstName = firstName;
    this.lastName = lastName;
    this.chat_id = chat_id;
    this.usernameCompains = usernameCompains;
    this.firstNameCompains = firstNameCompains;
    this.lastNameCompains = lastNameCompains;
  }
}
