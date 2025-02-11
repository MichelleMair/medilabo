import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Patient } from '../models/patient.model';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent implements OnInit{
  patient: Patient = { 
    id: null, 
    firstName: '', 
    lastName: '', 
    dateOfBirth: new Date(),
    gender:'', 
    address:'', 
    phoneNumber: '',
  };

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    //Check if ID is on parameters
    this.route.queryParams.subscribe((params) => {
      if (params['id']) {
        this.patientService.getPatientById(params['id']).subscribe({
          next: (data) => {
            if (data) {
              data.dateOfBirth = new Date(data.dateOfBirth).toISOString().split('T')[0];
              this.patient = data;
            } else {
              console.error("Patient not found for ID: ", params['id']);
            }
          },
          error: (err) => console.error("Error fetching patient data: ", err)
        });
      }
    });
  }

  savePatient() {
    if (!this.patient.firstName || !this.patient.lastName || !this.patient.dateOfBirth || !this.patient.gender) {
      alert('First name, Last name, Birthdate and Gender are required.');
      return;
    }

    if (this.patient.id) {
      this.patientService.updatePatient(this.patient).subscribe(() => {
        console.log('Patient updated successfully.');
        this.router.navigate(['/patients']);
      });
    } else {
      //Modifier la valeur du patId initialisé à zéro pour laisser le ms-backend-patient généré le patId auto-incrémenté
      this.patient = {...this.patient, patId: undefined };
      this.patientService.addPatient(this.patient).subscribe(() => {
        console.log('Patient added successfully.');
        this.router.navigate(['/patients']);
      });
    }
  }

  cancel() {
    this.router.navigate(['/patients']);
  }
}
