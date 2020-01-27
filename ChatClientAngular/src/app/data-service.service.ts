import { Injectable } from "@angular/core";
import { Message } from "./message";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class DataService {
  public group: string;
  public Messages: Array<Message> = [];
}
