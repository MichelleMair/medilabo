import { Component } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { NotesService } from '../services/notes.service';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent {
  patients: any[] = [];
  selectedPatientNotes: any[] | null = null;
  selectedPatientId: string = '';
  newNoteContent: string = '';
  errorMessage: string = '';

  constructor(private patientService: PatientService, private notesService: NotesService, private router: Router) {}

  ngOnInit() {
    this.loadPatients();
  }

  loadPatients() {
    this.patientService.getPatients().pipe(
      switchMap((patients) => this.patientService.mapPatientsWithPatIds(patients))
    ).subscribe({
      next: (patientsWithPatIds) => {
        this.patients = patientsWithPatIds;
        console.log('Patients with patId: ', this.patients);
      },
      error: (err) => {
        this.errorMessage = 'Failed to load patients. Please try again later.';
        console.error(this.errorMessage, err);
      }
    });
  }

  editPatient(patient: any) {
    this.router.navigate(['/patient-form'], { queryParams: { id: patient.id } }); //redirecting to patient form
  }

  deletePatient(id: number) {
    this.patientService.deletePatient(id).subscribe({
      next: () => {
        console.log('Patient deleted successfully!');
        this.loadPatients();
      },
      error: (err) => {
        console.error('Failed to delete patient.', err);
      }
    });
  }

  viewNotes(patientPatId: string) {
    console.log('View notes called for Patient ID: ', patientPatId);
    this.selectedPatientId = patientPatId;
    this.notesService.getNotesByPatientId(patientPatId).subscribe({
      next: (notes) => {
        console.log('Notes retrieved: ', notes);
        this.selectedPatientNotes = notes;
      },
      error: (err) => {
        console.error('Failed to load notes for patient.', err);
      }
    });
  }

  addNote() {
    if (!this.newNoteContent.trim()) {
      alert('Note content cannot be empty');
      return;
    }
    const newNote = {
      patId: this.selectedPatientId,
      patient: '',
      note: this.newNoteContent,
    };

    this.notesService.addNote(newNote).subscribe({
      next: () => {
      this.viewNotes(this.selectedPatientId);
      this.newNoteContent = '';
      },
      error: (err) => {
        console.error('Failed to add note.', err);
      }
    });
  }
}
