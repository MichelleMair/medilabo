import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root'})
export class DiabetesRiskService {
    private apiUrl = 'http://localhost:8082/api/risk';

    constructor(private http : HttpClient) {}

    getRiskLevel(patId: number): Observable<string> {
        return this.http.get(`${this.apiUrl}/${patId}` , { responseType: 'text' });
    }
}