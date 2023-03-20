INSERT INTO users (id, username, firstname, lastname)
values ('ec80480f-0f92-4776-b315-c6d2cf8bfc9a', 'app_admin', 'Admin', 'User');
INSERT INTO users (id, username, firstname, lastname)
values ('22ca52fe-ce4b-45c7-b362-dad760dd6b55', 'app_super_user', 'Super', 'User');
INSERT INTO users (id, username, firstname, lastname)
values ('d9ce8f60-4ea9-4e28-9ded-92aa95a6c423', 'app_user', 'Standard', 'User');

INSERT INTO documents (id, document_id)
values ('ca314dc6-56a5-43d0-9c09-70029d07a9f1', 1);
INSERT INTO documents (id, document_id)
values ('82489577-b95b-470e-97ae-99fce8bf9502', 2);
INSERT INTO documents (id, document_id)
values ('859836ab-fc94-4726-8fde-7157b0f28bd5', 3);

INSERT INTO user_permissions (user_permission_id, user_id, document_id, permission_type)
VALUES (UUID(), 'ec80480f-0f92-4776-b315-c6d2cf8bfc9a', 'ca314dc6-56a5-43d0-9c09-70029d07a9f1', 'READ');
INSERT INTO user_permissions (user_permission_id, user_id, document_id, permission_type)
VALUES (UUID(), '22ca52fe-ce4b-45c7-b362-dad760dd6b55', '82489577-b95b-470e-97ae-99fce8bf9502', 'READ');
INSERT INTO user_permissions (user_permission_id, user_id, document_id, permission_type)
VALUES (UUID(), 'd9ce8f60-4ea9-4e28-9ded-92aa95a6c423', '859836ab-fc94-4726-8fde-7157b0f28bd5', 'READ');
