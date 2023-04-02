INSERT INTO users (id, username, firstname, lastname)
values ('8e278aa3-b517-44d6-bdb9-303d3712217b', 'app_admin', 'Admin', 'User');
INSERT INTO users (id, username, firstname, lastname)
values ('81529f8a-724e-41bf-bd71-0fb537376742', 'app_super_user', 'Super', 'User');
INSERT INTO users (id, username, firstname, lastname)
values ('e426597f-aa25-4c02-8506-f1daedb1bfd9', 'app_user', 'Standard', 'User');

INSERT INTO documents (id, document_id)
values ('ca314dc6-56a5-43d0-9c09-70029d07a9f1', 1);
INSERT INTO documents (id, document_id)
values ('82489577-b95b-470e-97ae-99fce8bf9502', 2);
INSERT INTO documents (id, document_id)
values ('859836ab-fc94-4726-8fde-7157b0f28bd5', 3);

INSERT INTO user_permissions (user_permission_id, user_id, document_id, permission_type)
VALUES (UUID(), '76b76d80-e1ac-49ba-8b95-1a1ab197e03a', 'ca314dc6-56a5-43d0-9c09-70029d07a9f1', 'READ');
INSERT INTO user_permissions (user_permission_id, user_id, document_id, permission_type)
VALUES (UUID(), '5e8bd3a8-b282-421f-a838-636640d7a0dd', '82489577-b95b-470e-97ae-99fce8bf9502', 'READ');
INSERT INTO user_permissions (user_permission_id, user_id, document_id, permission_type)
VALUES (UUID(), 'e3bc4e23-ff71-44ba-b349-4a77fd6e5595', '859836ab-fc94-4726-8fde-7157b0f28bd5', 'READ');
