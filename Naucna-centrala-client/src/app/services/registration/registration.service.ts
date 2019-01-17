import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegistrationDetailsDTO } from '../../model/RegistrationDetailsDTO';

@Injectable()
export class RegistrationService {

    private baseUrl = '/api/register';

    constructor(private http: HttpClient) { }

    startProcess() : Observable<any>{
        return this.http.get<any[]>(`${this.baseUrl}`);
    }

    register(registration: RegistrationDetailsDTO): Observable<any> {
        const options = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        }
        return this.http.post<any>(`${this.baseUrl}`, registration, options);
    }

    getNextTask(username: string) {
        return this.http.get<any>(`${this.baseUrl}/task?username=${username}`);
    }

    getTaskData(taskId: number) {
        return this.http.get<any>(`${this.baseUrl}/task/${taskId}`);
    }

    caluclateCoordinates() {

    }

    verify(id: number, processInstanceId:string) {
        return this.http.put<any>(`${this.baseUrl}/verify/${id}/${processInstanceId}`, {});
    }

    confirm(formData, taskId) {
        return this.http.put<any>(`${this.baseUrl}/task/execute/${taskId}`, formData);
    }

}
