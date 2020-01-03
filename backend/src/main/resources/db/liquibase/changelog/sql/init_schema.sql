CREATE TYPE pr_priority as ENUM (
    'LOW',
    'NORMAL',
    'MAJOR',
    'CRITICAL',
    'BLOCKER'
    );

CREATE TYPE pr_ticket_type as ENUM (
    'DEFECT',
    'TASK',
    'STORY',
    'EPIC',
    'SUB-TASK'
    );

CREATE TYPE pr_resolution as ENUM (
    'UNRESOLVED',
    'FIXED',
    'NOT A BUG',
    'KNOWN ISSUE',
    'DUPLICATE',
    'CANNOT REPRODUCE',
    'CANCELED'
    );

CREATE TYPE pr_relation_type as ENUM (
    'RELATES TO',
    'BLOCKS',
    'BLOCKED BY',
    'DUPLICATES'
    );

CREATE TABLE pr_user
(
    user_id    SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    username   VARCHAR(100) NOT NULL,
    password   VARCHAR(100) NOT NULL
);

CREATE TABLE pr_role
(
    role_id SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL
);

CREATE TABLE pr_user_role
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL
);

CREATE TABLE pr_ticket
(
    ticket_id     SERIAL PRIMARY KEY,
    name          VARCHAR(100)   NOT NULL,
    description   VARCHAR(255),
    assignee_id   INTEGER,
    priority      pr_priority    NOT NULL,
    type          pr_ticket_type NOT NULL,
    reporter_id   INTEGER        NOT NULL,
    resolution    pr_resolution  NOT NULL,
    epic_id       INTEGER,
    due_date      TIMESTAMP,

    created_by    VARCHAR(255),
    created_when  TIMESTAMP,
    modified_by   VARCHAR(255),
    modified_id   TIMESTAMP,
    modified_when TIMESTAMP
);

CREATE TABLE pr_ticket_relation
(
    relation_id   SERIAL PRIMARY KEY,
    source_id     INTEGER          NOT NULL,
    target_id    INTEGER          NOT NULL,
    relation_type pr_relation_type NOT NULL,

    created_by    VARCHAR(255),
    created_when  TIMESTAMP,
    modified_by   VARCHAR(255),
    modified_id   TIMESTAMP,
    modified_when TIMESTAMP

);

CREATE TABLE pr_label
(
    label_id  SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    ticket_id INTEGER
);

CREATE TABLE pr_comment
(
    comment_id    BIGSERIAL PRIMARY KEY,
    text          VARCHAR(512) NOT NULL,
    ticket_id     INTEGER NOT NULL,

    created_by    VARCHAR(255),
    created_when  TIMESTAMP,
    modified_by   VARCHAR(255),
    modified_id   TIMESTAMP,
    modified_when TIMESTAMP
);

ALTER TABLE ONLY pr_user_role
    ADD CONSTRAINT fk_pr_user_role FOREIGN KEY (user_id) REFERENCES pr_user (user_id);
ALTER TABLE ONLY pr_user_role
    ADD CONSTRAINT fk_pr_role_user FOREIGN KEY (role_id) REFERENCES pr_role (role_id);

ALTER TABLE ONLY pr_ticket
    ADD CONSTRAINT fk_pr_ticket_assignee FOREIGN KEY (assignee_id) REFERENCES pr_user (user_id);
ALTER TABLE ONLY pr_ticket
    ADD CONSTRAINT fk_pr_ticket_reporter FOREIGN KEY (reporter_id) REFERENCES pr_user (user_id);
ALTER TABLE ONLY pr_ticket
    ADD CONSTRAINT fk_pr_ticket_epic FOREIGN KEY (epic_id) REFERENCES pr_ticket (ticket_id);

ALTER TABLE ONLY pr_ticket_relation
    ADD CONSTRAINT fk_pr_ticket_relation_source FOREIGN KEY (source_id) REFERENCES pr_ticket (ticket_id);
ALTER TABLE ONLY pr_ticket_relation
    ADD CONSTRAINT fk_pr_ticket_relation_related FOREIGN KEY (target_id) REFERENCES pr_ticket (ticket_id);

ALTER TABLE ONLY pr_label
    ADD CONSTRAINT fk_pr_label_ticket FOREIGN KEY (ticket_id) REFERENCES pr_ticket (ticket_id);

ALTER TABLE ONLY pr_comment
    ADD CONSTRAINT fk_pr_comment_ticket FOREIGN KEY (ticket_id) REFERENCES pr_ticket (ticket_id);

