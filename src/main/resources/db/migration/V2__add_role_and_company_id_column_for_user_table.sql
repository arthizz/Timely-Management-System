CREATE TABLE company_role(

    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    company_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_company_role
        FOREIGN KEY (company_id)
        REFERENCES company(id),

    CONSTRAINT uq_company_role
        UNIQUE(company_id, role_name)

);

ALTER TABLE users
ADD COLUMN company_id BIGINT NULL,
ADD COLUMN role_id BIGINT NULL,

ADD CONSTRAINT fk_user_company
    FOREIGN KEY (company_id)
    REFERENCES company(id),

ADD CONSTRAINT fk_user_role
    FOREIGN KEY (role_id)
    REFERENCES company_role(id);
