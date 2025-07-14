-- Usuário administrador com senha "123456" (BCrypt gerada previamente)
INSERT INTO users (id, username, password)
VALUES (1, 'admin', '$2a$10$8bPn2bouRHUSWTHHTvIvMOf0o6zbegKnlRZMFf/qnsMWADszCqSqO');

-- Role padrão
INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_USER');
