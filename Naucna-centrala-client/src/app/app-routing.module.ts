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
  {path: 'change-password', component: ChangePasswordComponent},
  


];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
