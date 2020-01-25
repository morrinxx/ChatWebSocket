import { Component, OnInit, Output, EventEmitter } from "@angular/core";

@Component({
  selector: "app-login-screen",
  templateUrl: "./login-screen.component.html",
  styleUrls: ["./login-screen.component.css"]
})
export class LoginScreenComponent implements OnInit {
  @Output() login: EventEmitter<Boolean> = new EventEmitter<Boolean>();
  constructor() {}

  ngOnInit() {}

  loginIn() {
    this.login.emit(false);
  }
}
