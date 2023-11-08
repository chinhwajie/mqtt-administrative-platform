import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevicesBrowserComponent } from './devices-browser.component';

describe('DevicesBrowserComponent', () => {
  let component: DevicesBrowserComponent;
  let fixture: ComponentFixture<DevicesBrowserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DevicesBrowserComponent]
    });
    fixture = TestBed.createComponent(DevicesBrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
