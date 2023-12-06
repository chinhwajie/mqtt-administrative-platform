import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceMoreInfoComponent } from './device-more-info.component';

describe('DeviceMoreInfoComponent', () => {
  let component: DeviceMoreInfoComponent;
  let fixture: ComponentFixture<DeviceMoreInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeviceMoreInfoComponent]
    });
    fixture = TestBed.createComponent(DeviceMoreInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
