INSERT INTO roles (id, description, name, is_system) VALUES
(1,'ADMIN', 'ADMIN', true),
(2,'USER', 'USER', true);

INSERT INTO accounts (id,email, password, status, username, created_by, created_time, updated_time)
VALUES
(1,'chungkoi113@gmail.com','$2a$10$5jPLqGyodh8YEEI775B1leFFF2Sd5GSZ.Xhnf/MOzQitD.roQ0OyK','ACTIVE','chungkoi113@gmail.com','','2022-03-14 16:45:29.142','2022-03-14 16:45:29.142'),
(2,'hoat.tm@tripi.vn','$2a$10$f.OnqKJpMPgxAPz7lKIeIOp4tZ1yO0u8AnBcpE9N33fbgKeVbbYQK','ACTIVE','hoat.tm@tripi.vn','','2022-03-14 16:47:49.328','2022-03-14 16:47:49.328');

INSERT INTO accounts_roles (id, account_id, role_id)
VALUES  (1,1,2),
        (3,1,1),
        (4,2,1),
        (2,2,2);

