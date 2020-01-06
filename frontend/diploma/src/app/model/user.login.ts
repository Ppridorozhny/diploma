export class UserLoginDTO {
  public usernameOrEmail: string;
  public password: string;

  constructor(usernameOrEmail: string, password: string) {
    this.password = password;
    this.usernameOrEmail = usernameOrEmail;
  }
}
