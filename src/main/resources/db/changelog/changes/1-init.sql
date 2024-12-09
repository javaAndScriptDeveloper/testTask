CREATE TABLE currencies
(
    id          BIGSERIAL PRIMARY KEY,
    rates       JSONB          NOT NULL,
    code        VARCHAR UNIQUE NOT NULL,
    modified_at TIMESTAMP      NOT NULL
);

CREATE INDEX currencies_code_idx ON currencies (code);
