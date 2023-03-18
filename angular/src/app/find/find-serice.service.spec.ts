import { TestBed } from '@angular/core/testing';

import { FindSericeService } from './find-serice.service';

describe('FindSericeService', () => {
  let service: FindSericeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FindSericeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
