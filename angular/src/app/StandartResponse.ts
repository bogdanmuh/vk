export class StandardResponse <T>{
  constructor(
    public success: boolean,
    public data: T,
    public error: string) {
    this.success = success;
    this.data = data;
    this.error = error;
  }
}
