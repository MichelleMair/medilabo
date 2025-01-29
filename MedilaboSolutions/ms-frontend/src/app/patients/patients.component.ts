import { Component , ChangeDetectorRef, NgZone } from '@angular/core';
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
  selectedPatientId: number = 0;
  newNoteContent: string = '';
  errorMessage: string = '';
  userRole: string | null = null;

  constructor(private cdr: ChangeDetectorRef, private ngZone : NgZone, private patientService: PatientService, private notesService: NotesService, private router: Router) {}

  ngOnInit() {
    console.log('Component initialized');

    this.userRole = localStorage.getItem('role');
    console.log("Rôle récupéré du localStorage: ", this.userRole);

    if(!this.userRole || this.userRole !== 'ADMIN' && this.userRole !== 'USER') {
      console.warn('Unauthorized user. Redirecting to login...');
      this.router.navigate(['/auth']);
    }
    this.loadPatients();
  }

  loadPatients() {
    this.patientService.getPatients().subscribe({
      next: (patients) => {
        console.log('Raw patients from API : ', patients);

        if (!Array.isArray(patients)) {
          console.error("ERREUR: 'patients' n'est pas un tableau!");
          return
        }

        patients.forEach(patient => {
          if (!patient.id) {
            console.error("Un patient n'a pas de 'id' valide: ", patient);
          }
        });
        this.patients = [...patients];
        console.log('Patients stored in component: ', this.patients);
        this.cdr.markForCheck();
        this.cdr.detectChanges();

        setTimeout(() => {
          console.log('Patients after change detection: ', this.patients);
        }, 1000);
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

  viewNotes(patId: number) {
    console.log('View notes called with patId: ', patId);
    if (!patId) {
      console.error('ERREUR: patId est null ou undefined pou run patient.');
      return;
    }
    if (patId <= 0) {
      console.error('Invalid Patiend ID. Cannot fetch notes.');
      return;
    }

    this.selectedPatientId = patId;
    console.log('Selected Patient ID updated to: ', this.selectedPatientId);
    console.log('View notes called for Patient ID: ', patId);

    this.notesService.getNotesByPatientId(patId).subscribe({
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
    if (this.selectedPatientId <= 0 || this.selectedPatientId === null) {
      console.error('No patient selected. Cannot add a note.');
      alert('Please select a patient before adding a note.');
      return;
    }

    // Trouver le patient correspondant à selectedPatientId
    const selectedPatient = this.patients.find(p => p.patId === this.selectedPatientId);

    if(!selectedPatient) {
      console.error('Patient not found. Cannot add note.');
      alert('Patient data is missing.');
      return;
    }

    const newNote = {
      patId: this.selectedPatientId,
      patient: selectedPatient.lastName,
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

  trackById(index: number, patient: any): string {
    console.log("trackById appelé pour: " , patient);
    return patient.id ? patient.id.toString() : index.toString();
  }
}
