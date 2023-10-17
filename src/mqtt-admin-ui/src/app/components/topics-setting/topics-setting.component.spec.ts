import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicsSettingComponent } from './topics-setting.component';

describe('TopicsSettingComponent', () => {
  let component: TopicsSettingComponent;
  let fixture: ComponentFixture<TopicsSettingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TopicsSettingComponent]
    });
    fixture = TestBed.createComponent(TopicsSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
