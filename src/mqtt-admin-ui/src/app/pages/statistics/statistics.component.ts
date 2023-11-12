import { Component } from '@angular/core';
import {dummyMessages} from "../../components/dummy-data";
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";
import {MENU_ICON, SEARCH_ICON} from "../../icons";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent {
  constructor(iconRegistry: MatIconRegistry,
              sanitizer: DomSanitizer) {
    iconRegistry.addSvgIconLiteral('my-search', sanitizer.bypassSecurityTrustHtml(SEARCH_ICON));
  }

  messages = dummyMessages;
}
