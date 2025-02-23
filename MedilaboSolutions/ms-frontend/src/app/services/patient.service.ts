import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable , forkJoin, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { NotesService } from './notes.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiUrl = environment.PATIENTS_SERVICE_URL;

  constructor(private http: HttpClient, private notesService: NotesService) { }

  getPatients(): Observable<any[]> {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error("Aucun token trouvé!");
      return throwError(() => new Error("Utilisateur non authentifié"));
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<any[]>(`${this.apiUrl}`, { headers, withCredentials: true }).pipe(
      map(patients => {
          return patients.map(p => ({
          id: p.id,
          firstName: p.firstName,
          lastName: p.lastName,
          age: p.age,
          dateOfBirth: p.dateOfBirth,
          patId: p.patId
        }));
      })
    );
  }

  getPatientById(id: string): Observable<any> {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error("Aucun token trouvé.");
      return throwError(() => new Error("Utilisateur non authentifié."));
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
  }

  addPatient(patient: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, patient);
  }

  updatePatient(patient: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${patient.id}`, patient);
  }

  deletePatient(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

}
