INSERT INTO training_types (training_type_name) VALUES ('FITNESS');
INSERT INTO training_types (training_type_name) VALUES ('YOGA');
INSERT INTO training_types (training_type_name) VALUES ('CARDIO');

INSERT INTO users (first_name, last_name, username, password, is_active) VALUES ('Mike', 'Brown', 'Mike.Brown', 'password', true);
INSERT INTO trainers (user_id, specialization_id) VALUES (1, 1);

INSERT INTO users (first_name, last_name, username, password, is_active) VALUES ('Jane', 'Doe', 'Jane.Doe', 'password', true);
INSERT INTO trainees (user_id, address, date_of_birth) VALUES (2, 'Kyiv', '1995-05-10');

INSERT INTO trainings (trainee_id, trainer_id, training_type_id, training_name, training_date, training_duration)
VALUES (1, 1, 1, 'Morning Fitness', '2026-09-01', 60);