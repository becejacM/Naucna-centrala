-- admin
-- Kuhinja007- => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (1, 'admin', '$2a$10$vAx6qdBF9CAlCeH1C.tzauqzhja6ARlXVuBi0.87ijnDhHTmFY9xG', 'M9SlrusZncekBbcYwmVaHBF7l6cEUARX2E0KbWCweSg=', 'admin', 'admin', 1, 'ADMIN');
-- Operator-1-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (2, 'operator1', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'operator1@mailinator.com', 'Marko', 'Markovic', 1, 'OPERATOR');
-- Operator-2-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role) values
  (3, 'operator2', '$2a$10$rRpzJm1LFO73jGfdqE4cJeW5wcpUzN1XItUvoHJlx6WyR9D0zjMuW', 'operator2@mailinator.com', 'Jova', 'Jovic', 1, 'OPERATOR');
  

insert into privilege(id, name) value (1, 'READ_PRIVILEGE');
insert into privilege(id, name) value (2, 'WRITE_PRIVILEGE');
insert into privilege(id, name) value (3, 'CHANGE_PASSWORD_PRIVILEGE');

insert into role(id, name) value(1, 'ADMIN');
insert into role(id, name) value(2, 'OPERATOR');

insert into roles_privileges(role_id, privilege_id) value (1,1);
insert into roles_privileges(role_id, privilege_id) value (1,2);
insert into roles_privileges(role_id, privilege_id) value (1,3);
insert into roles_privileges(role_id, privilege_id) value (2,1);
insert into roles_privileges(role_id, privilege_id) value (2,3);

insert into users_roles(user_id, role_id) value (1, 1);
insert into users_roles(user_id, role_id) value (2, 2);
insert into users_roles(user_id, role_id) value (3, 2);