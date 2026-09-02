ALTER TABLE time_log_pause
    ADD COLUMN time_pause TIMESTAMP NULL
    AFTER time_log_id;