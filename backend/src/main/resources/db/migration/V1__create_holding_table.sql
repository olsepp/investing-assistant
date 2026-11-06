CREATE TABLE holding (
    id            BIGSERIAL      PRIMARY KEY,
    ticker        VARCHAR(20)    NOT NULL UNIQUE,
    quantity      NUMERIC(19, 8) NOT NULL CHECK (quantity > 0),
    average_price NUMERIC(19, 6) NOT NULL CHECK (average_price >= 0),
    currency      VARCHAR(3)     NOT NULL,
    created_at    TIMESTAMPTZ    NOT NULL,
    updated_at    TIMESTAMPTZ    NOT NULL
);
