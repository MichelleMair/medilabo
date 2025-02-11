import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private usernameSubject = new BehaviorSubject<string | null>(localStorage.getItem('username'));
    username$ = this.usernameSubject.asObservable();

    setUsername(username: string) {
        localStorage.setItem('username', username);
        this.usernameSubject.next(username);
    }

    clearAuthData() {
        localStorage.clear();
        this.usernameSubject.next(null);
    }
}