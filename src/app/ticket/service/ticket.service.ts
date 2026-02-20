// src/app/services/ticket.service.ts
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Ticket } from '../model/ticket.model';

@Injectable({ providedIn: 'root' })
export class TicketService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/tickets';

  getTickets() {
    return this.http.get<Ticket[]>(this.apiUrl);
  }

  updateTicket(ticket: Ticket) {
    return this.http.put<Ticket>(`${this.apiUrl}/${ticket.id}`, ticket);
  }

  createTicket(title: string) {
    return this.http.post<Ticket>(this.apiUrl, { title, state: 'ToDo' });
  }

  deleteTicket(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
