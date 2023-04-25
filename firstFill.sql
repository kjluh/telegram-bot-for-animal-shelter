INSERT INTO adoptive_parent (name, phone_number, address)
            VALUES ('Boris', '89995556633', 'Piter'),
                   ('Petr', '89998889977', 'Moscow');

INSERT INTO pet (adoptive_parent_id, type, name, age, description)
            VALUES (1, 'dog', 'Shon', 12, 'small dog'),
                   (1, 'dog', 'Mark', 2, 'small dog'),
                   (2, 'cat', 'Push', 1, 'small cat');