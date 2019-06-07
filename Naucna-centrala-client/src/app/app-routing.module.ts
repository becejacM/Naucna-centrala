import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {HomeComponent} from './components/home/home.component';
import {OnlyLoggedInGuard} from './guard/only-logged-in.guard';
import {AuthenticationComponent} from './components/authentication/authentication.component';
import {AlreadyLoggedInGuard} from './guard/already-logged-in.guard';
import {ChangePasswordComponent} from './components/change-password/change-password.component';
import { RegistrationPageComponent } from './components/registration/registration-page/registration-page.component';
import { VerificationPageComponent } from './components/registration/verification-page/verification-page.component';
import { RegistrationErrorPageComponent } from './components/registration/registration-error-page/registration-error-page.component';
import { MagazinesPageComponent } from './components/publication/magazines-page/magazines-page.component';
import { SubmitPaperPageComponent } from './components/publication/submit-paper-page/submit-paper-page.component';
import { TasksPageComponent } from './components/publication/tasks-page/tasks-page.component';
import { TaskComponent } from './components/publication/task/task.component';
import { SearchlistComponent } from './components/searchlist/searchlist.component';
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
import { ResubmitComponent } from './components/publication/resubmit/resubmit.component';
import { CheckFormatComponent } from './components/publication/check-format/check-format.component';
import { CheckTematicComponent } from './components/publication/check-tematic/check-tematic.component';
import { ChooseReviewersComponent } from './components/publication/choose-reviewers/choose-reviewers.component';
import { AddReviewComponent } from './components/publication/add-review/add-review.component';
import { ChooseNewReviewerComponent } from './components/publication/choose-new-reviewer/choose-new-reviewer.component';
import { EditorRevisionComponent } from './components/publication/editor-revision/editor-revision.component';
import { AuthorRevisionComponent } from './components/publication/author-revision/author-revision.component';
import { EditorDecisionComponent } from './components/publication/editor-decision/editor-decision.component';
import { ChooseTaskComponent } from './components/choose-task/choose-task.component';


const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {path: 'login', component: AuthenticationComponent, canActivate: [AlreadyLoggedInGuard]},
  {path: 'registration', component: RegistrationPageComponent, canActivate: [AlreadyLoggedInGuard]},  
  {path: 'register/verify/:id/:processInstanceId', component: VerificationPageComponent},  
  {path: 'registration/error/:taskId', component: RegistrationErrorPageComponent, canActivate: [AlreadyLoggedInGuard]},    
  {path: 'home', component: HomeComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'start', component: StartComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'submit-paper-page', component: SubmitPaperPageComponent, canActivate: [OnlyLoggedInGuard]},    
  {path: 'tasks-list', component: SubmitPaperPageComponent, canActivate: [OnlyLoggedInGuard]}, 
  {
    path: 'tasks',
    component: TasksPageComponent,
    children: [
      { path: ':taskId', component: TaskComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/upload-paper', component: TaskUploadPaperComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/check-tematic', component: TaskCheckTematicComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/check-format', component: TaskCheckFormatComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/choose-magazine', component: MagazinesPageComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/subscribe', component: SubscribeComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/upload', component: UploadPaperComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/checkTematic', component: CheckTematicComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/checkFormat', component: CheckFormatComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/resubmit', component: ResubmitComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/chooseReviewers', component: ChooseReviewersComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/addReview', component: AddReviewComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/chooseNewReviewer', component: ChooseNewReviewerComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/editorRevision', component: EditorRevisionComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/authorRevision', component: AuthorRevisionComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/editorDecision', component: EditorDecisionComponent, canActivate: [OnlyLoggedInGuard] },
      { path: ':taskId/chooseTask', component: ChooseTaskComponent, canActivate: [OnlyLoggedInGuard] }

    ]
  },
  {path: 'change-password', component: ChangePasswordComponent},
  {path: 'search-and-buy', component: SearchlistComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'search-and-buy/:filename', component: PaperComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'success/:id', component: SuccessPaymentComponent},
  {path: 'fail/:id', component: FailPaymentComponent},
  {path: 'magazine', component: MagazinelistComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'subscribe/success/:id', component: SuccessSubscriptionComponent},
  {path: 'subscribe/fail/:id', component: FailSubscriptionComponent},
  {path: 'revizori', component: ReviewersComponent, canActivate: [OnlyLoggedInGuard]},
  {path: 'upload', component: UploadPaperComponent, canActivate: [OnlyLoggedInGuard]},


];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
