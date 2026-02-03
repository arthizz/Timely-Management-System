CREATE table users(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_level_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE table user_level(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_level_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE table user_profile(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100) NULL,
    address varchar(200) NOT NULL,
    profile_picture VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE table company(

    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(50) NOT NULL,
    company_address VARCHAR(100) NOT NULL,
    company_admin_email VARCHAR(50) NOT NULL,
    is_verified BOOLEAN NOT NULL,
    is_subscribed BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT  CURRENT_TIMESTAMP

);

CREATE table time_log(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    time_in TIMESTAMP NOT NULL,
    time_out TIMESTAMP DEFAULT NULL,
    is_night_shift BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE table time_log_pause(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    time_log_id BIGINT NOT NULL,
    time_resume TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);