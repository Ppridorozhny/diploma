ALTER TABLE ONLY pr_run
    ADD COLUMN duration BIGINT NOT NULL DEFAULT 60000;
