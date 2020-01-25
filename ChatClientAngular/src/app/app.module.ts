import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { MaterialModule } from "./material.module";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { LoginScreenComponent } from "./login-screen/login-screen.component";
import { MainPageComponent } from "./main-page/main-page.component";
import { RouterModule, Routes } from "@angular/router";

const appRoutes: Routes = [{ path: "main-page", component: MainPageComponent }];

@NgModule({
  declarations: [AppComponent, LoginScreenComponent, MainPageComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    RouterModule.forRoot(appRoutes, { enableTracing: true })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
