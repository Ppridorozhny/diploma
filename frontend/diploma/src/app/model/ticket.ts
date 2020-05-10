import {Priority} from "./priority";
import {TicketType} from "./ticket.type";
import {Resolution} from "./resolution";
import {Status} from "./status";

export class Ticket {
  id: number;
  name: string;
  description: string;
  priority: Priority;
  type: TicketType;
  status: Status;
  resolution: Resolution;
  dueDate: string;
  assigneeId: number;
  reporterId: number;
  epicId: number;
  labels: string[];
  projectId: number;

}
