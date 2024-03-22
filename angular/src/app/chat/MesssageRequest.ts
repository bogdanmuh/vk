
export class  MessageRequest {
  public date: Date = new Date();

  constructor(public sender: string,
              public recipient: string,
              public message: string,
              public chatId: number) {
    this.sender = sender;
    this.recipient = recipient;
    this.message = message;
    this.chatId = chatId;
  }
}
