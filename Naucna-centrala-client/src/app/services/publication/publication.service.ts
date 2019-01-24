import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MagazineDetails } from '../../model/MagazineDetails';

@Injectable()
export class PublicationService {

  private baseUrl = '/api/publication';

  constructor(private http: HttpClient) { }

  startProcess(): Observable<any> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  chooseMagazine(magazine: MagazineDetails): Observable<any> {
    const options = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }
    return this.http.post<any>(`${this.baseUrl}`+"/chooseMagazine", magazine, options);
}
}
