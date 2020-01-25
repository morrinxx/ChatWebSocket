import { Component } from "@angular/core";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent {
  title = "ChatClientAngular";
  check: Boolean = true;

  logedIn(bool: Boolean) {
    this.check = bool;
  }
}
