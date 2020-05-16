import {Status} from "./status";

export class ChangeStatus {

  ticketId: number;

  newStatus: Status;

  comment: string = '';

  constructor(ticketId: number) {
    this.ticketId = ticketId;
  }
}
