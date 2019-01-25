-- dodavanje urednickih odbora
insert into editorial_board(id) values (1);
insert into editorial_board(id) values (2);

-- autori
-- MDJ421054-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype) values
  (1, 'autor1', '$2a$10$h1VM/QaYFHRKfv9yG77S2uyLT63XUWTCdfusgicsfa560spVNZEsi', 'autor1@mailinator.com', 'autor1', 'autor1', 1, 'AUTHOR', 'Serbia', 'Novi Sad', 'Author');
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype) values
  (2, 'autor2', '$2a$10$h1VM/QaYFHRKfv9yG77S2uyLT63XUWTCdfusgicsfa560spVNZEsi', 'autor2@mailinator.com', 'autor2', 'autor2', 1, 'AUTHOR', 'Serbia', 'Novi Sad', 'Author');
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype) values
  (3, 'autor3', '$2a$10$h1VM/QaYFHRKfv9yG77S2uyLT63XUWTCdfusgicsfa560spVNZEsi', 'autor3@mailinator.com', 'autor3', 'autor3', 1, 'AUTHOR', 'Serbia', 'Novi Sad', 'Author');
  
-- editori
-- Operator-1-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype, title, editorial_board_id, is_main) values
  (4, 'editor1', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'editor1@mailinator.com', 'editor1', 'editor1', 1, 'EDITOR', 'Serbia', 'Novi Sad', 'Editor', 'dr', 1, 'yes');
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype, title, editorial_board_id, is_main) values
  (5, 'editor2', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'editor2@mailinator.com', 'editor2', 'editor2', 1, 'EDITOR', 'Serbia', 'Novi Sad', 'Editor', 'dr', 2, 'no');
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype, title, editorial_board_id, is_main) values
  (6, 'editor3', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'editor3@mailinator.com', 'editor3', 'editor3', 1, 'EDITOR', 'Serbia', 'Novi Sad', 'Editor', 'dr', 1, 'yes');
  
-- revieweri
-- Operator-1-bsep => password
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype, title) values
  (7, 'reviewer1', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'reviewer1@mailinator.com', 'reviewer1', 'reviewer1', 1, 'REVIEWER', 'Serbia', 'Novi Sad', 'Reviewer', 'dr');
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype, title) values
  (8, 'reviewer2', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'reviewer2@mailinator.com', 'reviewer2', 'reviewer2', 1, 'REVIEWER', 'Serbia', 'Novi Sad', 'Reviewer', 'dr');
insert into app_user(id, username, password,email, firstname, lastname, verified, role, state, city, dtype, title) values
  (9, 'reviewer3', '$2a$10$KbAWqhzc8.MlWoQxS.lkXOHT4tQ1j87CYbvpNG49FSmnw7lZt/zFe', 'reviewer3@mailinator.com', 'reviewer3', 'reviewer3', 1, 'REVIEWER', 'Serbia', 'Novi Sad', 'Reviewer', 'dr');

insert into privilege(id, name) value (1, 'READ_PRIVILEGE');
insert into privilege(id, name) value (2, 'WRITE_PRIVILEGE');
insert into privilege(id, name) value (3, 'CHANGE_PASSWORD_PRIVILEGE');

insert into role(id, name) value(1, 'AUTHOR');
insert into role(id, name) value(2, 'EDITOR');
insert into role(id, name) value(3, 'REVIEWER');

insert into roles_privileges(role_id, privilege_id) value (1,1);
insert into roles_privileges(role_id, privilege_id) value (1,2);
insert into roles_privileges(role_id, privilege_id) value (1,3);
insert into roles_privileges(role_id, privilege_id) value (2,1);
insert into roles_privileges(role_id, privilege_id) value (2,3);

insert into users_roles(user_id, role_id) value (1, 1);
insert into users_roles(user_id, role_id) value (2, 1);
insert into users_roles(user_id, role_id) value (3, 1);
insert into users_roles(user_id, role_id) value (4, 2);
insert into users_roles(user_id, role_id) value (5, 2);
insert into users_roles(user_id, role_id) value (6, 2);
insert into users_roles(user_id, role_id) value (7, 3);
insert into users_roles(user_id, role_id) value (8, 3);
insert into users_roles(user_id, role_id) value (9, 3);

-- dodavanje casopisa
insert into magazine(id, issn, name, payment_method, editorial_board_id) values (1, '12345', 'casopis 1 biologija', 1, 1);
insert into magazine(id, issn, name, payment_method, editorial_board_id) values (2, '741258', 'casopis 2 logika', 0, 2);

-- dodavanje naucnih oblasti
insert into scientific_field(id, scientific_field_name) values (1, 0);
insert into scientific_field(id, scientific_field_name) values (2, 1);
insert into scientific_field(id, scientific_field_name) values (3, 2);
insert into scientific_field(id, scientific_field_name) values (4, 3);
insert into scientific_field(id, scientific_field_name) values (5, 4);

-- dodela naucnih oblasti casopisima
insert into magazine_field(magazine_id, field_id) values (1, 2);
insert into magazine_field(magazine_id, field_id) values (2, 4);

-- dodela naucnih oblasti editorima
insert into editor_field(editor_id, field_id) values (4, 2);
insert into editor_field(editor_id, field_id) values (5, 4);
insert into editor_field(editor_id, field_id) values (6, 2);
insert into editor_field(editor_id, field_id) values (6, 5);

-- dodela naucnih oblasti revizorima
insert into reviewer_field(reviewer_id, field_id) values (7, 2);
insert into reviewer_field(reviewer_id, field_id) values (8, 2);
insert into reviewer_field(reviewer_id, field_id) values (8, 4);
insert into reviewer_field(reviewer_id, field_id) values (9, 2);
insert into reviewer_field(reviewer_id, field_id) values (9, 4);


-- dodela revizora casopisima
insert into reviewer_magazine(reviewer_id, magazine_id) values (7, 1);
insert into reviewer_magazine(reviewer_id, magazine_id) values (8, 1);
insert into reviewer_magazine(reviewer_id, magazine_id) values (8, 2);
insert into reviewer_magazine(reviewer_id, magazine_id) values (9, 1);

