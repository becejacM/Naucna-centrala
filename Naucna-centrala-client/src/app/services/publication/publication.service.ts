import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MagazineDetails } from '../../model/MagazineDetails';

@Injectable()
export class PublicationService {

  private baseUrl = '/api/publication';
  private uploadUrl = '/api/search';
  private searchUrl = '/api/search/search';

  private publishUrl = 'api/camunda/publish';
  
  constructor(private http: HttpClient) { }

  startProcess(): Observable<any> {
    //novo
    return this.http.get<any[]>(`${this.publishUrl}`+ "/startPublishProccess");
  }
  getMagazines(): Observable<any> {
    //novo
    return this.http.get<any[]>(`${this.publishUrl}`);
  }
  chooseMagazine(magazine: MagazineDetails, taskId:String): Observable<any> {
    //novo
    const options = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }
    return this.http.post<any>(`${this.publishUrl}/chooseMagazine/${taskId}`, magazine, options);
  }

  getMagazineWithTask(taskId: string) {
    //novo
    return this.http.get<any>(`${this.publishUrl}/task/${taskId}`);
  };
  getPaperInfoWithTask(taskId: string) {
    //novo
    return this.http.get<any>(`${this.publishUrl}/task/${taskId}/paperInfo`);
  };
  getPaperPDFWithTask(taskId: string) {
    //novo
    return this.http.get<any>(`${this.publishUrl}/task/${taskId}/paperPDF`);
  };
  subscribe(taskId: string) {
    //novo
    return this.http.get<any>(`${this.publishUrl}/subscribe/${taskId}`);
  };
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
    //novo
    return this.http.get<any>(`${this.publishUrl}/userTasks`);
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
    //novo
    const options = {
      headers: new HttpHeaders()
    };
    let formData: FormData = new FormData();
    formData.append('file', book);

    return this.http.post<any>(`${this.publishUrl}/temporaryupload`, formData, options);
  }

  save(paper, taskId) {
    //novo
    return this.http.post(`${this.publishUrl}/submit/${taskId}`, paper);
  }

  answerTematic(answer, taskId) {
    //novo
    return this.http.post(`${this.publishUrl}/tematicAnswer/${taskId}`, answer);
  }

  answerFormat(answer, taskId) {
    //novo
    return this.http.post(`${this.publishUrl}/formatAnswer/${taskId}`, answer);
  }

  download(simpleQuery) {
    //novo
    return this.http.post(`${this.searchUrl}/download`, simpleQuery, { responseType: 'blob' });
  }


  resubmit(paper, taskId) {
    //novo
    return this.http.post(`${this.publishUrl}/resubmit/${taskId}`, paper);
  }
}
