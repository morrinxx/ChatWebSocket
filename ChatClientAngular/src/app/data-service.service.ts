import { Injectable } from "@angular/core";
import { Message } from "./message";
import { Observable } from "rxjs";
import { User } from "./user";

@Injectable({
  providedIn: "root"
})
export class DataService {
  public group: string;
  public Messages: Array<Message> = [];
  public user: User = new User("", "", false);
  public websocket: WebSocket;
}
