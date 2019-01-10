import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {CommonModule} from '@angular/common';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgxPermissionsModule} from 'ngx-permissions';
import {RecaptchaModule} from 'ng-recaptcha';
import {RecaptchaFormsModule} from 'ng-recaptcha/forms';

import {AppComponent} from './app.component';
import {HomeComponent} from './components/home/home.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {AuthenticationComponent} from './components/authentication/authentication.component';
import {NotFoundPageComponent} from './components/not-found-page/not-found-page.component';

import {AuthenticationService} from './services/authentication/authentication.service';
import {TokenInterceptorService} from './services/token-interceptor/token-interceptor.service';

import {AlreadyLoggedInGuard} from './guard/already-logged-in.guard';
import {OnlyLoggedInGuard} from './guard/only-logged-in.guard';

import {PaginationComponent} from './components/pagination/pagination.component';
import {AppRoutingModule} from './app-routing.module';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { SortableColumnComponent } from './components/sortable-column/sortable-column.component';
import { SortService } from './services/sort/sort.service';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AuthenticationComponent,
    HomeComponent,
    NotFoundPageComponent,
    PaginationComponent,
    SortableColumnComponent,
    ChangePasswordComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    ToastrModule.forRoot(),
    NgxPermissionsModule.forRoot(),
    HttpClientModule,
    AppRoutingModule,
    BrowserModule,
    RecaptchaModule.forRoot(), // Keep in mind the "forRoot"-magic nuances!
    RecaptchaFormsModule, // if you need forms support
 
  ],
  providers: [
    AuthenticationService,
    SortService,
    OnlyLoggedInGuard,
    AlreadyLoggedInGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
