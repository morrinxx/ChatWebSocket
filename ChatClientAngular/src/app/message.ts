export class Message {
  constructor(
    public type: string,
    public username: string,
    public password: string,
    public group: string,
    public text: string
  ) {}
}
