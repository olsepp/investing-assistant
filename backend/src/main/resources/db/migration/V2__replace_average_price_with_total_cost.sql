-- Store what was paid in total instead of a per-share average: it is what the user knows
-- (e.g. from the broker confirmation) and it is exact, while a per-share average often isn't.

ALTER TABLE holding ADD COLUMN total_cost NUMERIC(19, 6);

UPDATE holding SET total_cost = quantity * average_price;

ALTER TABLE holding
    ALTER COLUMN total_cost SET NOT NULL,
    ADD CONSTRAINT holding_total_cost_check CHECK (total_cost >= 0),
    DROP COLUMN average_price;
