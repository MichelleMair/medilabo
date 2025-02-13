import { NgModule } from "@angular/core";
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from "./app.component";
import { AuthComponent } from "./auth/auth.component";
import { PatientsComponent } from "./patients/patients.component";
import { PatientFormComponent } from "./patient-form/patient-form.component";
import { AuthInterceptor } from "./auth/auth.interceptor";

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
        RouterModule.forRoot(routes),
        BrowserModule,
        FormsModule,
        HttpClientModule
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        },
    ],
    bootstrap: [AppComponent]
})
export class AppModule {}