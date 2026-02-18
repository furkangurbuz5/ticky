import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ScrumBoardComponent } from "./board/board";

@Component({
  selector: 'app-root',
  imports: [ScrumBoardComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('ticky');
}
