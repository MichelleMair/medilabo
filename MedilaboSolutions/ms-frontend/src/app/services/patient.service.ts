import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiUrl = 'http://localhost:8082/api/patients';

  constructor(private http: HttpClient) { }

  getPatients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  addPatient(patient: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, patient);
  }

  updatePatient(patient: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${patient.id}`, patient);
  }

  deletePatient(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

}
