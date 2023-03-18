export class  MessageResponse {
  constructor(public message: string,
              public from: string,
              public date: Date,
              public to: string) {
    this.message = message;
    this.from = from;
    this.date = date;
    this.to = to;
  }
}
