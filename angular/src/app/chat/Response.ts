import {Message} from "./Message";

export class Response {
  constructor(public list: Message [],
              public online: boolean,
              public chatId: number) {
    this.list = list;
    this.online = online;
    this.chatId = chatId;
  }
}
