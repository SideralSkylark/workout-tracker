import { TestBed } from '@angular/core/testing';

import { WorkoutSplitService } from './workout-split.service';

describe('WorkoutSplitService', () => {
  let service: WorkoutSplitService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkoutSplitService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
