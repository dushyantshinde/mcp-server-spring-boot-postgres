-- Insert sample quantity items
INSERT INTO quantity_items (name, quantity, created_at, updated_at)
VALUES
    ('Laptop', 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Monitor', 50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Keyboard', 200, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Mouse', 150, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Headset', 75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample allocations
INSERT INTO quantity_item_allocations (quantity_item_id, quantity, status, created_at, updated_at)
VALUES
    (1, 20, 'consumed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 30, 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 15, 'consumed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 10, 'retired', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 50, 'available', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 25, 'consumed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 15, 'disposed', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
