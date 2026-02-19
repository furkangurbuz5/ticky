package com.gurbuz.hearty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gurbuz.hearty.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
    
}
