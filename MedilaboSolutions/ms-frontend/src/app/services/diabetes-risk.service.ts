import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({ providedIn: 'root'})
export class DiabetesRiskService {
    private apiUrl = environment.RISK_SERVICE_URL;

    constructor(private http : HttpClient) {}

    getRiskLevel(patId: number): Observable<string> {
        return this.http.get(`${this.apiUrl}/${patId}` , { responseType: 'text' });
    }
}