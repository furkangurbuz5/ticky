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
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScrumBoardComponent {
  private ticketService = inject(TicketService);
  tickets = signal<Ticket[]>([]);
  states: TicketState[] = ['ToDo', 'In-progress', 'Done'];
  newTicketTitle = '';

  constructor() {
    this.loadTickets();
  }

  loadTickets() {
    this.ticketService.getTickets().subscribe((tickets) => {
      this.tickets.set(tickets);
    });
  }

  addTicket() {
    if (!this.newTicketTitle.trim()) return;
    this.ticketService.createTicket(this.newTicketTitle).subscribe(() => {
      this.loadTickets();
      this.newTicketTitle = '';
    });
  }

  onDragStart(event: DragEvent, ticket: Ticket) {
    event.dataTransfer?.setData('text/plain', JSON.stringify(ticket));
  }

  onDrop(event: DragEvent, state: TicketState) {
    event.preventDefault();
    const data = event.dataTransfer?.getData('text/plain');
    if (data) {
      const ticket = JSON.parse(data) as Ticket;
      this.ticketService.updateTicket({ ...ticket, state }).subscribe(() => {
        this.loadTickets();
      });
    }
  }

  onDragOver(event: DragEvent) {
    event.preventDefault();
  }
}
