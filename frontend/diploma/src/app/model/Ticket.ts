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

export enum Priority {
  LOW = "Low",
  NORMAL = "Normal",
  MAJOR = "Major",
  CRITICAL = "Critical",
  BLOCKER = "Blocker",
}

export enum TicketType {
  DEFECT = "Defect",
  TASK = "Task",
  SUBTASK = "Sub-task",
  EPIC = "Epic",
  STORY = "Story",
}

export enum Resolution {
  RESOLVER = "Resolved",
  UNRESOLVED = "Unresolved",
  NOT_A_BUG = "Not a bug",
  KNOWN_ISSUE = "Known Issue",
  DUPLICATE = "Duplicate",
  CANNOT_REPRODUCE = "Cannot Reproduce",
  CANCELED = "Canceled",
}
