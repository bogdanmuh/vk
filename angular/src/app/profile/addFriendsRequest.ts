export class AddFriendsRequest {
  constructor(public username: string,
              public friend: string) {
    this.friend = friend;
    this.username = username;
  }
}
