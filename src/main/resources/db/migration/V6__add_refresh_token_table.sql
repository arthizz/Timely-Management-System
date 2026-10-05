CREATE TABLE refresh_token (
       id BIGINT PRIMARY KEY AUTO_INCREMENT,
       token_hash VARCHAR(100) NOT NULL UNIQUE,
       user_id BIGINT NOT NULL,
       expires_at TIMESTAMP NOT NULL,
       created_at TIMESTAMP NOT NULL,
       revoked BOOLEAN NOT NULL,

       CONSTRAINT fk_refresh_token_user
           FOREIGN KEY (user_id) REFERENCES users(id)
);