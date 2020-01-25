import { Component } from "@angular/core";
import { User } from "./user";
import { Message } from "./message";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent {
  title = "ChatClientAngular";
  user: User = new User("", "", false);
  check: Boolean = true;
  wsUri: string = "ws://localhost:8025/websockets/whatsapp";
  websocket: WebSocket;
  sendToGroup: string = "school";
  logBuffer: string = "";
  inMessages: Array<Message> = [];
  myGroups: Array<String> = ["school", "family", "sports", "friends"];

  logedIn(newUser: User) {
    this.user = newUser;
    this.check = this.user.switchOver;
  }

  doConnect() {
    this.websocket = new WebSocket(this.wsUri);
    //onOpen
    this.websocket.onopen = evt => (this.logBuffer += "Websocket connected\n");
    //onMessage
    this.websocket.onmessage = evt => {
      this.logBuffer += evt.data + "\n";
      let message: Message = JSON.parse(evt.data);
      // data message recieved
      if (message.typ == "data") {
        this.inMessages.push(message);
      }
      // groups message recieved
      else if (message.typ == "groups") {
        this.myGroups = message.value;
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

  doLogin() {
    this.websocket.send(
      '{"typ":"login", "username":"' +
        this.user.username +
        '", "password":"' +
        this.user.password +
        '"}'
    );
  }
}
