import { Component, OnInit } from '@angular/core';
import { PatientService } from '../services/patient.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css']
})
export class PatientFormComponent implements OnInit{
  patient = { id: null, name: '', age: null };

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    //Check if ID is on parameters
    this.route.queryParams.subscribe((params) => {
      if (params['id']) {
        this.patientService.getPatients().subscribe((data) => {
          this.patient = data.find((p: any) => p.id === +params['id']);
        });
      }
    });
  }

  savePatient() {
    if (!this.patient.name || !this.patient.age) {
      console.error('Name and age are required');
      return;
    }
    if (this.patient.id) {
      this.patientService.updatePatient(this.patient).subscribe(() => {
        console.log('Patient updated successfully.');
        this.router.navigate(['/patients']);
      });
    } else {
      this.patientService.addPatient(this.patient).subscribe(() => {
        console.log('Patient added successfully.');
        this.router.navigate(['/patients']);
      });
    }
  }
}
