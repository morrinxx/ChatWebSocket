import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router, NavigationStart } from "@angular/router";
import { DataService } from "../data-service.service";

@Component({
  selector: "app-group",
  templateUrl: "./group.component.html",
  styleUrls: ["./group.component.css"]
})
export class GroupComponent implements OnInit {
  group: string;
  constructor(public dataservice: DataService) {}

  ngOnInit() {
    this.group = this.dataservice.group;
  }
}
