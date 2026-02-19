package com.gurbuz.hearty.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gurbuz.hearty.model.Ticket;
import com.gurbuz.hearty.model.TicketState;

@RestController()
public class TicketController {

    @GetMapping("/tickets")
    ResponseEntity<Ticket> getTickets(){
        Ticket ticket = new Ticket(1, "New Ticket!", TicketState.ToDo);
        
        return new ResponseEntity<Ticket>(ticket, HttpStatusCode.valueOf(200));
    }
}
