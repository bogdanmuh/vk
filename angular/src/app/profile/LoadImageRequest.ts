export class LoadImageRequest {
  constructor(public image: File,
              public username: string) {
    this.image = image;
    this.username = username;

  }
}
