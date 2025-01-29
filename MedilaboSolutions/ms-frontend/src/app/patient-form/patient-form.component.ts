import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent implements OnInit{
  patient = { id: null, firstName: '', lastName: '', dateOfBirth: '', gender:'', address:'', phoneNumber: '', patId: 0 };

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
    if (!this.patient.firstName || !this.patient.lastName || !this.patient.dateOfBirth || !this.patient.gender || !this.patient.address || !this.patient.phoneNumber) {
      alert('All fields are required.');
      return;
    }

    if (this.patient.id) {
      this.patientService.updatePatient(this.patient).subscribe(() => {
        console.log('Patient updated successfully.');
        this.router.navigate(['/patients']);
      });
    } else {
      this.patient.patId = Math.floor(Math.random() * 10000);
      this.patientService.addPatient(this.patient).subscribe(() => {
        console.log('Patient added successfully.');
        this.router.navigate(['/patients']);
      });
    }
  }
}
