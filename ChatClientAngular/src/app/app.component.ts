import { Component } from "@angular/core";
import { User } from "./user";
import { Message } from "./message";
import { DataService } from "./data-service.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent {
  title = "ChatClientAngular";
  user: User = new User("", "", false);
  check: Boolean = true;
  wsUri: string = "ws://localhost:8080/chat/a_a";
  websocket: WebSocket = new WebSocket(this.wsUri);
  sendToGroup: string = "school";
  logBuffer: string = "";
  inMessages: Array<Message> = [];
  myGroups: Array<String> = ["school", "family", "sports", "friends"];
  loginSuccess: Boolean = false;

  constructor(public dataservice?: DataService) {}

  logedIn(newUser: User) {
    this.user = newUser;
    this.doConnect();
    this.delay(200);
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => {
      if (!this.loginSuccess) {
        alert("Username or Password is wrong!");
      } else {
        this.check = this.user.switchOver;
      }
    });
  }

  doSend(group, text): void {
    console.log(group);
    console.log(this.dataservice.group);
    this.sendToGroup = this.dataservice.group;
    let message: Message = new Message(
      "message",
      this.user.username,
      this.user.password,
      text,
      this.sendToGroup
    );
    this.inMessages.push(message);
    this.dataservice.Messages = this.inMessages;
    this.websocket.send(JSON.stringify(message));
  }

  doConnect() {
    this.websocket = new WebSocket(
      "ws://localhost:8080/chat/" +
        this.user.username +
        "_" +
        this.user.password
    );
    //onOpen
    this.websocket.onopen = evt => {
      this.loginSuccess = true;
    };
    //onMessage
    this.websocket.onmessage = evt => {
      this.logBuffer += evt.data + "\n";
      let message: Message = JSON.parse(evt.data);
      // data message recieved
      if (message.type == "message") {
        this.inMessages.push(message);
        this.dataservice.Messages = this.inMessages;
      }
    };
    //onError
    this.websocket.onerror = evt => (this.logBuffer += "Error\n");
    //onClose
    this.websocket.onclose = evt => (this.logBuffer += "Websocket closed\n");
  }

  doDisconnect(): void {
    this.websocket.close();
  }
}
