package com.gurbuz.hearty.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gurbuz.hearty.model.Ticket;
import com.gurbuz.hearty.model.TicketState;
import com.gurbuz.hearty.repository.TicketRepository;

@Service
public class TicketService {
    
    private TicketRepository ticketRepository;

    TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(int id, TicketState state) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setState(state);
        return ticketRepository.save(ticket);
    }
}
