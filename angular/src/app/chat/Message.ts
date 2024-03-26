export class  Message {
  public event: string = "";
  constructor(public message: string,
              public from: string,
              public date: Date,
              public recipient: string) {
    this.message = message;
    this.from = from;
    this.date = date;
    this.recipient = recipient;
  }
}
