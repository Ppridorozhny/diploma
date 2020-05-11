import {TicketShort} from "./ticketShort";
import {RelationType} from "./relationType";

export class TicketRelation {
  id: number;
  relationType: RelationType = RelationType.RELATES_TO;
  source: TicketShort = new TicketShort();
  target: TicketShort = new TicketShort();

}
