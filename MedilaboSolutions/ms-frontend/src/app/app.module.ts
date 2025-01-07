import { NgModule } from "@angular/core";
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from "./app.component";
import { AuthComponent } from "./auth/auth.component";
import { PatientsComponent } from "./patients/patients.component";
import { PatientFormComponent } from "./patient-form/patient-form.component";

//Routes
const routes: Routes = [
    { path: '', redirectTo: 'auth', pathMatch: 'full' },
    { path: 'auth', component: AuthComponent },
    { path: 'patients', component: PatientsComponent },
    { path: 'patient-form', component: PatientFormComponent },
    { path: '**', redirectTo: 'auth' },
];

@NgModule ({
    declarations: [
        AppComponent,
        AuthComponent,
        PatientsComponent,
        PatientFormComponent
    ],

    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        RouterModule.forRoot (routes)
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {}