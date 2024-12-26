import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeaderComponent } from './components/header/header.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent], // Add DashboardComponent here
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'], // Note: Fix the typo here (`styleUrl` -> `styleUrls`)
  standalone: true
})
export class AppComponent {
  title = 'frontend';

  constructor(private router: Router) {}

  get hideHeader(): boolean {
    const routesWithoutHeader = ['/', '/register'];
    return routesWithoutHeader.includes(this.router.url);
  }
}
