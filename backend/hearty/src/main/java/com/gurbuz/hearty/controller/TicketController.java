package com.gurbuz.hearty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gurbuz.hearty.model.Ticket;
import com.gurbuz.hearty.model.TicketState;
import com.gurbuz.hearty.service.TicketService;

@RestController()
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable int id, @RequestParam TicketState state) {
        return ticketService.updateTicket(id, state);
    }

    @GetMapping("/test")
    ResponseEntity<Ticket> getTickets() {
        Ticket ticket = new Ticket("New Ticket!", TicketState.ToDo);

        return new ResponseEntity<Ticket>(ticket, HttpStatusCode.valueOf(200));
    }
}
