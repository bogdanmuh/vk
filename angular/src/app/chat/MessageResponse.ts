export class  MessageResponse {
  public event: string = "";
  constructor(public message: string,
              public sender: string,
              public date: Date,
              public recipient: string) {
    this.message = message;
    this.sender = sender;
    this.date = date;
    this.recipient = recipient;
  }
}
