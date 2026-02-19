package com.gurbuz.hearty.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gurbuz.hearty.model.Ticket;

@RestController()
public class TicketController {

    @GetMapping("/tickets")
    ResponseEntity<Ticket> getTickets(){
        System.out.println("Getting tickets?");
        return new ResponseEntity<Ticket>(HttpStatusCode.valueOf(200));

    }
}
