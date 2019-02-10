import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MagazineDetails } from '../../model/MagazineDetails';

@Injectable()
export class PublicationService {

  private baseUrl = '/api/publication';
  private uploadUrl = '/api/search';
  
  constructor(private http: HttpClient) { }

  startProcess(): Observable<any> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  chooseMagazine(magazine: MagazineDetails): Observable<any> {
    const options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }
    return this.http.post<any>(`${this.baseUrl}` + "/chooseMagazine", magazine, options);
  }

  getRevizori(naucnaOblast:any): Observable<any> {
    const options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }
    return this.http.get<any>(`${this.uploadUrl}` + "/reviewers/"+ naucnaOblast, options);
  }

  getRevizoriSvi(): Observable<any> {
    const options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }
    return this.http.get<any>(`${this.uploadUrl}` + "/reviewers/all" , options);
  }
  getTasks() {
    return this.http.get<any>(`${this.baseUrl}/tasks`);
  };

  getTask(id: string) {
    return this.http.get<any>(`${this.baseUrl}/tasks/${id}`);
  };

  executeTask(id: string, params: any) {
    return this.http.post<any>(`${this.baseUrl}/tasks/${id}`, params);
  };

  claimTask(id: string) {
    return this.http.get<any>(`${this.baseUrl}/tasks/${id}/claim`);
  };

  upload(book: any): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    let formData: FormData = new FormData();
    formData.append('file', book);

    return this.http.post<any>(`${this.uploadUrl}/upload`, formData, options);
  }

  temporaryupload(book: any): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    let formData: FormData = new FormData();
    formData.append('file', book);

    return this.http.post<any>(`${this.uploadUrl}/temporaryupload`, formData, options);
  }
}
