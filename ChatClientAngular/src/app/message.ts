export class Message {
  constructor(
    public type: string,
    public username: string,
    public password: string,
    public msg: string,
    public group: string
  ) {}
}
