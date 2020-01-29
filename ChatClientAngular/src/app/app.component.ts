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
  ip: String = "localhost";
  check: Boolean = true;
  wsUri: string = "ws://" + this.ip + ":8080/chat/";
  sendToGroup: string = "school";
  logBuffer: string = "";
  myGroups: Array<String> = ["school", "family", "sports", "friends"];
  loginSuccess: Boolean = false;

  constructor(public dataservice?: DataService) {}

  logedIn(newUser: User) {
    this.dataservice.user = newUser;
    this.doConnect();
    this.delay(200);
  }

  async delay(ms: number) {
    await new Promise(resolve => setTimeout(() => resolve(), ms)).then(() => {
      if (this.dataservice.websocket.readyState == 3) {
        this.loginSuccess = false;
      }
      if (!this.loginSuccess) {
        alert("Username or Password is wrong!");
      } else {
        this.check = this.dataservice.user.switchOver;
      }
    });
  }

  doSend(group, text): void {
    console.log(this.dataservice.websocket);
    this.sendToGroup = this.dataservice.group;
    console.log(this.dataservice.user.username);
    let message: Message = new Message(
      "message",
      this.dataservice.user.username,
      "",
      text,
      this.sendToGroup
    );

    this.dataservice.websocket.send(JSON.stringify(message));
  }

  doConnect() {
    this.dataservice.websocket = new WebSocket(
      "ws://" +
        this.ip +
        ":8080/chat/" +
        this.dataservice.user.username +
        "_" +
        this.dataservice.user.password
    );
    console.log(this.dataservice.websocket);
    console.log(this.dataservice.user.username);
    //onOpen
    this.dataservice.websocket.onopen = evt => {
      this.loginSuccess = true;
    };
    //onMessage
    this.dataservice.websocket.onmessage = evt => {
      this.logBuffer += evt.data + "\n";
      let message: Message = JSON.parse(evt.data);
      console.log(message);
      // data message recieved
      if (message.type == "message") {
        this.dataservice.Messages.push(message);
      }
    };
    //onError
    this.dataservice.websocket.onerror = evt => console.log("error");
    //onClose
    this.dataservice.websocket.onclose = evt =>
      (this.logBuffer += "Websocket closed\n");
  }

  doDisconnect(): void {
    this.dataservice.websocket.close();
  }
}
