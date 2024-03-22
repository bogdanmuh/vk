export class  NewChatRequest {
  constructor(public name: string,
              public participantsUsername: string[]) {
    this.name = name;
    this.participantsUsername = participantsUsername;
  }
}
