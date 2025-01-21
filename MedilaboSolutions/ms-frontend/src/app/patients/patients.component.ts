import { Component } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { NotesService } from '../services/notes.service';
import { Router } from '@angular/router';

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

  constructor(private patientService: PatientService, private notesService: NotesService, private router: Router) {}

  ngOnInit() {
    this.loadPatients();
  }

  loadPatients() {
    this.patientService.getPatients().subscribe((data) => {
      this.patients = data;
    });
  }

  editPatient(patient: any) {
    this.router.navigate(['/patient-form'], { queryParams: { id: patient.id } }); //redirecting to patient form
  }

  deletePatient(id: number) {
    this.patientService.deletePatient(id).subscribe(() => {
      console.log('Patient deleted successfully!');
      this.loadPatients();
    });
  }

  viewNotes(patientId: string) {
    this.selectedPatientId = patientId;
    this.notesService.getNotesByPatientId(patientId).subscribe((notes) => {
      this.selectedPatientNotes = notes;
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

    this.notesService.addNote(newNote).subscribe(() => {
      this.viewNotes(this.selectedPatientId);
      this.newNoteContent = '';
    });
  }
}
