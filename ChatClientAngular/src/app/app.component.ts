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
  wsUri: string = "ws://localhost:8080/chat/";
  websocket: WebSocket;
  sendToGroup: string = "school";
  logBuffer: string = "";
  inMessages: Array<Message> = [];
  myGroups: Array<String> = ["school", "family", "sports", "friends"];
  loginSuccess: Boolean;

  logedIn(newUser: User) {
    this.user = newUser;
    this.doConnect();
    if (!this.loginSuccess) {
      alert("Username or Password is wrong!");
    } else {
      this.check = this.user.switchOver;
    }
  }

  doSend(group, text): void {
    this.sendToGroup = group;
    let message: Message = new Message(
      "message",
      this.user.username,
      this.user.password,
      this.sendToGroup,
      text
    );
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
<<<<<<< Updated upstream
      console.log("LKADJFLKASJDLKASJDLK")
=======
      console.log(this.loginSuccess);
>>>>>>> Stashed changes
    };
    //onMessage
    this.websocket.onmessage = evt => {
      this.logBuffer += evt.data + "\n";
      let message: Message = JSON.parse(evt.data);
      // data message recieved
      if (message.type == "message") {
        this.inMessages.push(message);
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
