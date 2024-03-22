export class JwtResponse {
  token: string;
  type: string;
  username: string;
  roles: string[];
  id: number;

  constructor(token: string, type: string, username: string, id: number) {
    this.token = token;
    this.type = type;
    this.username = username;
    this.id = id;
    this.roles = ['user'];
  }
}
