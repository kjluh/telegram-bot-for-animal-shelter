INSERT INTO adoptive_parent (name, phone_number, address)
            VALUES ('Boris', '89995556633', 'Piter'),
                   ('Petr', '89998889977', 'Moscow');

INSERT INTO pet (adoptive_parent_id, type, name, age, description)
            VALUES (1, 'dog', 'Shon', 12, 'small dog'),
                   (1, 'dog', 'Mark', 2, 'small dog'),
                   (2, 'cat', 'Push', 1, 'small cat');

INSERT INTO report (parent_id, pet_id, photo_id, diet, health, behavior)
            VALUES (1, 1, 'sdjasd7h3t7f8whwfd68g234y78w3t7y', 'diet_test', 'health_test', 'behavior_test'),
                   (2, 2, 'dfasdd42rf3g5yh76u6j67j', 'diet_test2', 'health_test2', 'behavior_test2'),
                   (1, 1, 'sdgf8ywr78fh673t7g37th8d2', 'diet_test3', 'health_test3', 'behavior_test3');