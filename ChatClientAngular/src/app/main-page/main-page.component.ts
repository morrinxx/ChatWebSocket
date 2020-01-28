import { Component } from "@angular/core";
import { ChangeDetectorRef, OnDestroy } from "@angular/core";
import { MediaMatcher } from "@angular/cdk/layout";
import { DataService } from "../data-service.service";
import { AppComponent } from "../app.component";
import { from } from "rxjs";

@Component({
  selector: "app-main-page",
  templateUrl: "./main-page.component.html",
  styleUrls: ["./main-page.component.css"]
})
export class MainPageComponent implements OnDestroy {
  mobileQuery: MediaQueryList;
  value = "";
  check = false;
  checkM = false;
  ac: AppComponent = new AppComponent(this.dataservice);

  fillerNav: string[] = ["cd", "cd", "cd", "cd"];

  private _mobileQueryListener: () => void;

  constructor(
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    public dataservice: DataService
  ) {
    this.mobileQuery = media.matchMedia("(max-width: 600px)");
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  clicked(nav: string) {
    this.dataservice.group = nav;
    this.check = true;
    this.checkM = true;
  }

  sendMsg() {
    this.ac.doSend(this.dataservice.group, this.value);
  }
}
