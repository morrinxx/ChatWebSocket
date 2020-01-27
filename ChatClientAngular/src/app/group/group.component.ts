import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router, NavigationStart } from "@angular/router";
import { DataService } from "../data-service.service";
import { Message } from "../message";

@Component({
  selector: "app-group",
  templateUrl: "./group.component.html",
  styleUrls: ["./group.component.css"]
})
export class GroupComponent implements OnInit {
  public messages: Array<Message> = [];
  group: string;
  constructor(public dataservice: DataService) {}

  ngOnInit() {
    this.group = this.dataservice.group;
    this.messages = this.dataservice.Messages;
  }
}
