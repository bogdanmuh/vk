export class LastMessage {
  constructor(public username: string,
              public date: Date ,
              public message: string ,
              public firstName: string ,
              public lastName: string ,
              public chat_id: string,
              public usernameCompanion: string ,
              public firstNameCompanion: string ,
              public lastNameCompanion: string ,
              public chatName: string ,
              ) {
    this.username = username;
    this.date = date;
    this.message = message;
    this.firstName = firstName;
    this.lastName = lastName;
    this.chat_id = chat_id;
    this.usernameCompanion = usernameCompanion;
    this.firstNameCompanion = firstNameCompanion;
    this.lastNameCompanion = lastNameCompanion;
    this.chatName = chatName;
  }
}
