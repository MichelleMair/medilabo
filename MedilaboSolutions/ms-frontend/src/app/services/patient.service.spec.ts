import { TestBed } from '@angular/core/testing';
import { PatientService } from './patient.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';


describe('PatientService', () => {
  let service: PatientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PatientService]
    });
    service = TestBed.inject(PatientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
