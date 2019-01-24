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
import { SimpleNotificationsModule } from 'angular2-notifications';

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
import { RegistrationService } from './services/registration/registration.service';

import { RegistrationFormComponent } from './components/registration/registration-form/registration-form.component';
import { RegistrationPageComponent } from './components/registration/registration-page/registration-page.component';
import { RegistrationErrorPageComponent } from './components/registration/registration-error-page/registration-error-page.component';
import { VerificationPageComponent } from './components/registration/verification-page/verification-page.component';
import { MagazinesPageComponent } from './components/publication/magazines-page/magazines-page.component';
import { PublicationService } from './services/publication/publication.service';
import { SubmitPaperPageComponent } from './components/publication/submit-paper-page/submit-paper-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    AuthenticationComponent,
    HomeComponent,
    NotFoundPageComponent,
    PaginationComponent,
    SortableColumnComponent,
    ChangePasswordComponent,
    RegistrationFormComponent,
    RegistrationPageComponent,
    RegistrationErrorPageComponent,
    VerificationPageComponent,
    MagazinesPageComponent,
    SubmitPaperPageComponent,
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
    SimpleNotificationsModule.forRoot()    
  ],
  providers: [
    AuthenticationService,
    SortService,
    RegistrationService,
    OnlyLoggedInGuard,
    AlreadyLoggedInGuard,
    PublicationService,
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
