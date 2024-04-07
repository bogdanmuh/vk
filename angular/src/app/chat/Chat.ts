export class Chat {
  constructor(public id: string,
              public from: string,
              public date: Date,
              public recipient: string) {
    this.id = id;
    this.from = from;
    this.date = date;
    this.recipient = recipient;
  }
}
