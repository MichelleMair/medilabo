import { Component } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent {
  patients: any[] = [];

  constructor(private patientService: PatientService, private router: Router) {}

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
}
