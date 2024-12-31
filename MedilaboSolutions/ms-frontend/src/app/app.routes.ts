import { Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { PatientsComponent } from './patients/patients.component';
import { PatientFormComponent } from './patient-form/patient-form.component';

export const routes: Routes = [
    { path: '', redirectTo: 'auth', pathMatch: 'full' },
    { path: 'auth', component: AuthComponent },
    { path: 'patients' , component: PatientsComponent},
    { path: 'patient-form', component: PatientFormComponent},
    { path: '**', redirectTo: 'auth' }
];
