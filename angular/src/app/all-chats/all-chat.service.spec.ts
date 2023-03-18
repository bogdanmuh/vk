import { TestBed } from '@angular/core/testing';

import { AllChatService } from './all-chat.service';

describe('AllChatService', () => {
  let service: AllChatService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AllChatService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
