import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable , forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { NotesService } from './notes.service';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiUrl = 'http://localhost:8082/api/patients';

  constructor(private http: HttpClient, private notesService: NotesService) { }

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

  //map patients data (cluster: patients) with patId in notes' cluster
  mapPatientsWithPatIds(patients: any[]): Observable<any[]> {
    return forkJoin(
      patients.map((patient) =>
        this.notesService.getNotesByPatientId(patient.id).pipe(
          map((notes: any[]) => {
            const transformedPatient = {
              ...patient, 
              patId: notes.length> 0 ? notes[0].patId : null,
            };
            console.log('Transformed patient: ', transformedPatient);
            return transformedPatient
          })
        )
      )
    );
  }

}
