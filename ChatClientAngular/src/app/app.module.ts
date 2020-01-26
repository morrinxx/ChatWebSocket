import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";

import { MaterialModule } from "./material.module";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { LoginScreenComponent } from "./login-screen/login-screen.component";
import { MainPageComponent } from "./main-page/main-page.component";
import { RouterModule, Routes } from "@angular/router";
import { GroupComponent } from "./group/group.component";

const appRoutes: Routes = [
  {
    path: "group",
    component: GroupComponent,
    data: { title: "Group Component" }
  },
  {
    path: "main",
    component: MainPageComponent,
    data: { title: "Main Component" }
  },
  {
    path: "login",
    component: LoginScreenComponent,
    data: { title: "Login Component" }
  }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginScreenComponent,
    MainPageComponent,
    GroupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    RouterModule.forRoot(appRoutes, { useHash: true })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
