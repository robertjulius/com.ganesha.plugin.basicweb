
-- MS_MODULE --
DELETE FROM ms_module WHERE id = '011user_maintenance';
DELETE FROM ms_module WHERE id = '012usergroup_maintenance';
DELETE FROM ms_module WHERE id = '010maintenance';
DELETE FROM ms_module WHERE id = '021module_maintenance';
DELETE FROM ms_module WHERE id = '020developer';
INSERT INTO ms_module (id, create_by, create_date, update_by, update_date, first_entry, description, name, parent, rec_status) VALUES ('010maintenance', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, NULL, 'Parent for all maintenances utility', 'Maintenance', NULL, 'A');
INSERT INTO ms_module (id, create_by, create_date, update_by, update_date, first_entry, description, name, parent, rec_status) VALUES ('011user_maintenance', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, NULL, 'Menu for user maintenance', 'User', '010maintenance', 'A');
INSERT INTO ms_module (id, create_by, create_date, update_by, update_date, first_entry, description, name, parent, rec_status) VALUES ('012usergroup_maintenance', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, '/modules/usergroupmaintenance/initial.action', 'Menu for user group maintenance', 'User Group', '010maintenance', 'A');
INSERT INTO ms_module (id, create_by, create_date, update_by, update_date, first_entry, description, name, parent, rec_status) VALUES ('013reset_user_session', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, '/modules/resetusersession/initial.action', 'Menu to reset user session', 'Reset User Session', '010maintenance', 'A');
INSERT INTO ms_module (id, create_by, create_date, update_by, update_date, first_entry, description, name, parent, rec_status) VALUES ('020developer', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, NULL, 'Parent for all developer utility', 'Developer', NULL, 'A');
INSERT INTO ms_module (id, create_by, create_date, update_by, update_date, first_entry, description, name, parent, rec_status) VALUES ('021module_maintenance', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, '/modules/module/initial.action', 'Menu for module maintenance', 'Module', '020developer', 'A');

-- MS_USERGROUP
DELETE FROM ms_user_group;
INSERT INTO ms_user_group (id, create_by, create_date, update_by, update_date, description, name, rec_status) VALUES ('developer', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'This group just for developer only', 'Developer', 'A');

-- MS_USER
DELETE FROM ms_user;
INSERT INTO ms_user (id, create_by, create_date, update_by, update_date, name, password, user_id, user_group_id, rec_status) VALUES ('qpalzmwoskxn', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'Robert Julius', '1234', '1234', 'developer', 'A');
INSERT INTO ms_user (id, create_by, create_date, update_by, update_date, name, password, user_id, user_group_id, rec_status) VALUES ('qpalzm', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP, 'Robert 2', '123', '123', 'developer', 'A');

-- MS_PRIVILEGE
DELETE FROM ms_privilege;
INSERT INTO ms_privilege (user_group_id, module_id) VALUES ('developer', '011user_maintenance');
INSERT INTO ms_privilege (user_group_id, module_id) VALUES ('developer', '012usergroup_maintenance');
INSERT INTO ms_privilege (user_group_id, module_id) VALUES ('developer', '013reset_user_session');
INSERT INTO ms_privilege (user_group_id, module_id) VALUES ('developer', '021module_maintenance');