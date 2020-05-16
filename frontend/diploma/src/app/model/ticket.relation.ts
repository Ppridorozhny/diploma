import {TicketShort} from "./ticket.short";
import {RelationType} from "./relation.type";

export class TicketRelation {
  id: number;
  relationType: RelationType = RelationType.RELATES_TO;
  source: TicketShort = new TicketShort();
  target: TicketShort = new TicketShort();

}
