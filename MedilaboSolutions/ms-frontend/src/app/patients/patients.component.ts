import { Component , ChangeDetectorRef } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { NotesService } from '../services/notes.service';
import { DiabetesRiskService } from '../services/diabetes-risk.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent {
  patients: any[] = [];
  paginatedPatients : any [] = [] ;
  selectedPatientNotes: any[] | null = null;
  selectedPatientId: number = 0;
  newNoteContent: string = '';
  errorMessage: string = '';
  userRole: string | null = null;
  currentPage: number = 1;
  patientsPerPage: number = 10;
  totalPages: number = 1;

  constructor(
    private cdr: ChangeDetectorRef,  
    private patientService: PatientService, 
    private notesService: NotesService, 
    private diabetesRiskService: DiabetesRiskService, 
    private router: Router) {}

  ngOnInit() {
    console.log('Component initialized');

    this.userRole = localStorage.getItem('role');

    if(!this.userRole || this.userRole !== 'ADMIN' && this.userRole !== 'USER') {
      console.warn('Unauthorized user. Redirecting to login...');
      this.router.navigate(['/auth']);
    }
    this.loadPatients();
  }

  loadPatients() {
    this.patientService.getPatients().subscribe({
      next: (patients) => {
        if (!Array.isArray(patients)) {
          console.error("ERREUR: 'patients' n'est pas un tableau!");
          return
        }

        patients.forEach(patient => {
          if (!patient.id) {
            console.error("No valid id for : ", patient);
          }

          // Récupération du niveau de risque 
          this.diabetesRiskService.getRiskLevel(patient.patId).subscribe({
            next: (risk) => {
              patient.riskLevel = risk;
              this.cdr.markForCheck(); 
            },
            error : (err) => {
              console.error(`Failed to retrieve risk level for patient with ID ${patient.patId}: `, err);
            }
          })
        });
        
        this.patients = [...patients];
        this.totalPages = Math.ceil(this.patients.length / this.patientsPerPage);
        this.updatePaginatedPatients();
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

  addNewPatient() {
    this.router.navigate(['/patient-form']);
  }

  deletePatient(id: string) {
    this.patientService.deletePatient(id).subscribe({
      next: () => {
        this.loadPatients();
      },
      error: (err) => {
        console.error('Failed to delete patient.', err);
      }
    });
  }

  viewNotes(patId: number) {
    console.log('Cliked on patient with patId: ', patId);

    if (!patId) {
      console.error('ERREUR: patId is null or undefined for this patient.');
      return;
    }
    if (patId <= 0) {
      console.error('Invalid Patiend ID. Cannot fetch notes.');
      return;
    }

    console.log('Cliked on patient with patId: ${patId}');

    this.selectedPatientId = patId;
    console.log('Selected Patient ID updated to: ', this.selectedPatientId);

    this.notesService.getNotesByPatientId(patId).subscribe({
      next: (notes) => {
        console.log('Notes fetched : ', notes);
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

        // Recalculer le risque de diabète après ajout de la note
        this.diabetesRiskService.getRiskLevel(this.selectedPatientId).subscribe ({
          next : (updatedRiskLevel) => {
            selectedPatient.riskLevel = updatedRiskLevel;
            this.cdr.markForCheck();
          },
          error : (err) => {
            console.error('Failed to recalculate diabetes risk level.', err);
          }
        });
      },
      error: (err) => {
        console.error('Failed to add note.', err);
      }
    });
  }

  // Mise en place d'une pagination pour l'affichage de la liste des patients 
  updatePaginatedPatients() {
    const start = (this.currentPage - 1) * this.patientsPerPage;
    const end = start + this.patientsPerPage;
    this.paginatedPatients = this.patients.slice(start, end);
  }

  nextPage() {
    if((this.currentPage * this.patientsPerPage) < this.patients.length) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  //------------ gestion pagination - fin --------------------

  
  trackById(index: number, patient: any): string {
    return patient.id ? patient.id.toString() : index.toString();
  }
}
