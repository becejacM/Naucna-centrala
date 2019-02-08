import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class SearchService {
  private baseUrl = '/api/search';
  private searchUrl = '/api/search/search';
  private kupiUrl = '/api/trans';
  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/sep`);
  }

  getById(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return this.http.post<any>(`${this.searchUrl}/getByFilename`, simpleQuery, options);
  }
  download(simpleQuery) {
    return this.http.post(`${this.searchUrl}/download`, simpleQuery, { responseType: 'blob' });
  }

  kupi(transactionRequestDto): Observable<any> {
    return this.http.post(`${this.kupiUrl}/initi`, transactionRequestDto);
  }

  success(id): Observable<any> {
    return this.http.get(`${this.kupiUrl}/success/`+id);
  }

  fail(id): Observable<any> {
    return this.http.get(`${this.kupiUrl}/fail/`+id);
  }

  proveriKupovinu(transactionRequestDto): Observable<any> {
    return this.http.post(`${this.kupiUrl}/proveraKupovine`,transactionRequestDto);
  }
  searchByContent(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/queryParser`, simpleQuery, options);
  }
  searchTerm(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/term`, simpleQuery, options);
  }
  searchFuzzy(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/fuzzy`, simpleQuery, options);
  }
  searchPhrase(simpleQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/phrase`, simpleQuery, options);
  }

  searchBoolean(advancedQuery): Observable<any> {
    const options = {
      headers: new HttpHeaders()
    };
    return  this.http.post<any>(`${this.searchUrl}/boolean`, advancedQuery, options);
  }
}
