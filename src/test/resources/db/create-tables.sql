CREATE TABLE data_user (
                      username VARCHAR(255) PRIMARY KEY,
                      birth_date DATE NOT NULL,
                      country_of_residence VARCHAR(255) NOT NULL,
                      creation_date DATE,
                      phone_number VARCHAR(64),
                      gender VARCHAR(64),
                      deleted BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_deleted ON data_user (deleted);