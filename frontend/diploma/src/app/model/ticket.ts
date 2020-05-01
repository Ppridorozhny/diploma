import {Priority} from "./priority";
import {TicketType} from "./ticket.type";
import {Resolution} from "./resolution";

export class Ticket {
  id: number;
  name: string;
  description: string;
  priority: Priority;
  type: TicketType;
  resolution: Resolution;
  dueDate: string;
  assigneeId: number;
  reporterId: number;
  epicId: number;
  labels: string[];

}
