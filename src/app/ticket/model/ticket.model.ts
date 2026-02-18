export type TicketState = "ToDo" | "In-progress" | "Done";

export interface Ticket {
  id: number;
  title: string;
  state: TicketState;
}
