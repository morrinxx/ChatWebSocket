import { Component, OnInit, Output, EventEmitter, Input } from "@angular/core";
import { User } from ".././user";

@Component({
  selector: "app-login-screen",
  templateUrl: "./login-screen.component.html",
  styleUrls: ["./login-screen.component.css"]
})
export class LoginScreenComponent implements OnInit {
  @Input() user: User;
  @Output() login: EventEmitter<User> = new EventEmitter<User>();
  constructor() {}

  ngOnInit() {}

  loginIn() {
    this.login.emit(this.user);
  }
}
