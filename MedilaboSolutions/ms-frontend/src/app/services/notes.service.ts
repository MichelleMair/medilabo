import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })
  export class NotesService {
    private apiUrl = 'http://localhost:8082/api/notes';

    constructor(private http: HttpClient) {}

    getNotesByPatientId(patId: number): Observable<any[]> {
      console.log(`Fetching notes for patientId: ${patId}`);
        return this.http.get<any[]>(`${this.apiUrl}/${patId}`);
    }

    addNote(note:any): Observable<any> {
        return this.http.post<any>(this.apiUrl, note);
    }
  }