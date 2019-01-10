import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient) {
  }

  authenticateUser(username: string, pass: string) {
    const authenticationRequestBody = {username: username, password: pass};
    console.log(authenticationRequestBody);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    return this.http.post('/api/users/login', authenticationRequestBody);
  }

  changePassword(changePasswordObject: object) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    return this.http.put('/api/users/change-password', changePasswordObject, {responseType: 'text'});
  }

}
