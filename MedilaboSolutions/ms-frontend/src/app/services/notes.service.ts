import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
  })
  export class NotesService {
    private apiUrl = environment.NOTES_SERVICE_URL;

    constructor(private http: HttpClient) {}

    getNotesByPatientId(patId: number): Observable<any[]> {
      console.log(`Fetching notes for patientId: ${patId}`);
        return this.http.get<any[]>(`${this.apiUrl}/${patId}`);
    }

    addNote(note:any): Observable<any> {
        return this.http.post<any>(this.apiUrl, note);
    }
  }