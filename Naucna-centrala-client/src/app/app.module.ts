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
import { TasksListComponent } from './components/publication/tasks-list/tasks-list.component';
import { TasksPageComponent } from './components/publication/tasks-page/tasks-page.component';
import { TaskComponent } from './components/publication/task/task.component';
import { SearchlistComponent } from './components/searchlist/searchlist.component';
import { SearchService } from './services/search/search.service';
import { PaperComponent } from './components/paper/paper.component';
import { SuccessPaymentComponent } from './components/success-payment/success-payment.component';
import { FailPaymentComponent } from './components/fail-payment/fail-payment.component';
import { MagazinelistComponent } from './components/magazinelist/magazinelist.component';
import { FailSubscriptionComponent } from './components/fail-subscription/fail-subscription.component';
import { SuccessSubscriptionComponent } from './components/success-subscription/success-subscription.component';
import { ReviewersComponent } from './components/reviewers/reviewers.component';
import { UploadPaperComponent } from './components/upload-paper/upload-paper.component';
import { TaskUploadPaperComponent } from './components/publication/task-upload-paper/task-upload-paper.component';
import { TaskCheckTematicComponent } from './components/publication/task-check-tematic/task-check-tematic.component';
import { TaskCheckFormatComponent } from './components/publication/task-check-format/task-check-format.component';
import { StartComponent } from './components/publication/start/start.component';
import { SubscribeComponent } from './components/publication/subscribe/subscribe.component';
import { CheckFormatComponent } from './components/publication/check-format/check-format.component';
import { CheckTematicComponent } from './components/publication/check-tematic/check-tematic.component';
import { ResubmitComponent } from './components/publication/resubmit/resubmit.component';
import { ChooseReviewersComponent } from './components/publication/choose-reviewers/choose-reviewers.component';
import { ChooseNewReviewerComponent } from './components/publication/choose-new-reviewer/choose-new-reviewer.component';
import { AddReviewComponent } from './components/publication/add-review/add-review.component';
import { EditorRevisionComponent } from './components/publication/editor-revision/editor-revision.component';
import { AuthorRevisionComponent } from './components/publication/author-revision/author-revision.component';
import { EditorDecisionComponent } from './components/publication/editor-decision/editor-decision.component';

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
    TasksListComponent,
    TasksPageComponent,
    TaskComponent,
    SearchlistComponent,
    PaperComponent,
    SuccessPaymentComponent,
    FailPaymentComponent,
    MagazinelistComponent,
    FailSubscriptionComponent,
    SuccessSubscriptionComponent,
    ReviewersComponent,
    UploadPaperComponent,
    TaskUploadPaperComponent,
    TaskCheckTematicComponent,
    TaskCheckFormatComponent,
    StartComponent,
    SubscribeComponent,
    CheckFormatComponent,
    CheckTematicComponent,
    ResubmitComponent,
    ChooseReviewersComponent,
    ChooseNewReviewerComponent,
    AddReviewComponent,
    EditorRevisionComponent,
    AuthorRevisionComponent,
    EditorDecisionComponent,
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
    SearchService,
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
