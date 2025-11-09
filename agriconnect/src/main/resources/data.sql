INSERT INTO buyer (name, phone_number, email)
VALUES
    ('Saurav Nayak',  '9876543210', 'saurav.nayak@example.com'),
    ('Anirudh RaviChandran',  '8876543210', 'anirudh.ravichandran@example.com'),
    ('Tiger Shroff',  '7876543210', 'dishant.verma@example.com'),
    ('Bahubali prabhas',  '7176543210', 'bahubali.prabhas@example.com'),
    ('Kabir Singh',  '887655555', 'kabir.singh@example.com');

INSERT INTO seller (name, company, phone_number, email)
VALUES
    ('Mukesh Ambani', 'RelianceAgro','900100010', 'mukesh.ambani@example.com'),
    ( 'Rahul Dravid', 'AdaniAgro','900100020',  'rahul.dravid@example.com'),
    ('Sharukh Khan', 'FilmOrganicAgri','900111110',  'shahrukh.khan@example.com');

INSERT INTO orders (order_date, order_id, seller_id, buyer_id)
VALUES
    ('2025-10-22 10:30:00', '1', 1, 2),
    ('2025-10-22 11:00:00', '2', 2, 2),
    ('2025-10-22 09:45:00', '3', 3, 3),
    ('2025-10-22 14:00:00', '4', 1, 1),
    ('2025-10-22 16:15:00', '5', 1, 4),
    ('2025-10-22 08:30:00', '6', 2, 5);