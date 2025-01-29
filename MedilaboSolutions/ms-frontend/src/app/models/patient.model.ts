export interface Patient {
    id?: string | null; //id null pour les nouveaux patients
    firstName: string;
    lastName: string;
    dateOfBirth: Date;
    gender: string;
    address: string;
    phoneNumber: string;
    patId?: number; //Optionnel car généré par le backend
}