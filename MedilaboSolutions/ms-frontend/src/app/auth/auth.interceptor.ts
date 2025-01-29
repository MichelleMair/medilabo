import { Injectable } from "@angular/core";
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from "@angular/common/http";
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private router : Router) {}


    intercept(request: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
        // Récupérer le token stocké dans le localStorage
        const token = localStorage.getItem('token');
        if (token) {
            //Ajouter le token à l'en-tête Authorization
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`,
                    'Cache-Control': 'no-store, no-cache, must-revalidate, proxy-revalidate',
                },
            });
            // Log pour vérifier que le token est bien ajouté 
            console.log("Token ajouté dans l'en-tête: ", request.headers.get("Authorization"));
        }
        return next.handle(request).pipe(
            catchError((error : HttpErrorResponse) => {
                if (error.status === 401 || error.status === 403) {
                    // Non autorisé, redirection vers la page de login
                    localStorage.removeItem('token');
                    localStorage.removeItem('role');
                    this.router.navigate(['/auth']);
                }
                return throwError(() => error);
            })
        );
    }
}
