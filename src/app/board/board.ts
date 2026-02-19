import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { TicketService } from '../ticket/service/ticket.service';
import { Ticket, TicketState } from '../ticket/model/ticket.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-scrum-board',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './board.html',
  styleUrl: './board.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ScrumBoardComponent {
  private ticketService = inject(TicketService);
  tickets = signal<Ticket[]>([]);
  states: TicketState[] = ['ToDo', 'In-progress', 'Done'];
  newTicketTitle = '';

  constructor() {
    this.loadTickets();
  }

  loadTickets(): void {
    this.ticketService.getTickets().subscribe((tickets) => {
      this.tickets.set(tickets);
    });
  }

  addTicket(): void {
    if (!this.newTicketTitle.trim()) return;
    this.ticketService.createTicket(this.newTicketTitle).subscribe(() => {
      this.loadTickets();
      this.newTicketTitle = '';
    });
  }

  onDragStart(event: DragEvent, ticket: Ticket): void {
    event.dataTransfer?.setData('text/plain', JSON.stringify(ticket));
    event.dataTransfer?.setDragImage(event.target as HTMLElement, 0, 0);
    (event.target as HTMLElement).classList.add('opacity-30');
  }

  onDrop(event: DragEvent, state: TicketState): void {
    event.preventDefault();
    (event.currentTarget as HTMLElement).classList.remove('bg-gray-200');
    const data = event.dataTransfer?.getData('text/plain');
    if (data) {
      const ticket = JSON.parse(data) as Ticket;
      this.ticketService.updateTicket({ ...ticket, state }).subscribe(() => {
        this.loadTickets();
      });
    }
  }

  onDragEnd(event: DragEvent): void {
    (event.target as HTMLElement).classList.remove('opacity-30');
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    (event.currentTarget as HTMLElement).classList.add('bg-gray-200');
  }

  onDragLeave(event: DragEvent) {
    (event.currentTarget as HTMLElement).classList.remove('bg-gray-200');
  }
}
