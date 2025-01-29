import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable , forkJoin, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { NotesService } from './notes.service';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiUrl = 'http://localhost:8082/api/patients';

  constructor(private http: HttpClient, private notesService: NotesService) { }

  getPatients(): Observable<any[]> {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error("Aucun token trouvé!");
      return throwError(() => new Error("Utilisateur non authentifié"));
    }
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<any[]>(`${this.apiUrl}`, { headers }).pipe(
      map(patients => {
          console.log("Réponse brute de l'API(patient.service.ts): ", patients);
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

  deletePatient(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  //map patients data (cluster: patients) with patId in notes' cluster
  mapPatientsWithPatIds(patients: any[]): Observable<any[]> {
    return forkJoin(
      patients.map((patient) =>
        this.notesService.getNotesByPatientId(patient.patId).pipe(
          map((notes: any[]) => ({
              ...patient, 
              notesCount: notes.length,
          }))
        )
      )
    );
  }

}
