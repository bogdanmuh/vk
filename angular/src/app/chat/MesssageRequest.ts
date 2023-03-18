
export class  MessageRequest {
  public date: Date = new Date();
  public message: string = "";
  constructor(public from: string,
              public to: string) {
    this.from = from;
    this.to = to;
  }
}
