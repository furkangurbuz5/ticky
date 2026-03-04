package com.gurbuz.hearty.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TicketState state;

  @Column(nullable = false)
  private String points;

  public Ticket() {
  }

  public Ticket(String title, TicketState state) {
    this.title = title;
    this.state = state;
  }

  //testing purposes
  public Ticket(Integer id, String title, TicketState state, String points) {
    this.id = id;
    this.title = title;
    this.state = state;
    this.points = points;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public TicketState getState() {
    return state;
  }

  public void setState(TicketState state) {
    this.state = state;
  }

  public String getPoints(){
    return this.points;
  }

  public void setPoints(String points){
    this.points = points;
  }
}
