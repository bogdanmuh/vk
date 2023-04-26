
export class  MessageRequest {
  public date: Date = new Date();

  constructor(public from: string,
              public to: string,
              public message: string) {
    this.from = from;
    this.to = to;
    this.message = message;
  }
}
