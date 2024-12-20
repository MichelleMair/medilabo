import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PatientService } from '../services/patient.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-patient-form',
  imports: [FormsModule],
  templateUrl: './patient-form.component.html',
  styleUrl: './patient-form.component.css'
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
    if (this.patient.id) {
      //Updating existing patient
      this.patientService.updatePatient(this.patient).subscribe(() => {
        console.log('Patient updated successfully!');
        this.router.navigate(['/patients']);
      });
    } else {
      //Create new patient
      this.patientService.addPatient(this.patient).subscribe(() => {
        console.log('Patient added successfully!');
        this.router.navigate(['/patients']); //redirecting to patient list
      });
    }
  }
}
