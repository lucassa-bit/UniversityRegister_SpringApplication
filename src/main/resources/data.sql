INSERT INTO user_parent (
        tipo,
        id,
        birth_date,
        email,
        id_number,
        login,
        name,
        password,
        user_type
    )
Values (
        'E',
        1,
        '2001-04-28',
        'admin@gmail.com',
        '2321321',
        'Admin',
        'Administrador',
        '$2a$10$bHbvyQT1px0uBdPt.oy8g.4ZYxZxsRJlv3YIk6cxzzYU667o9DpGq',
        0
    );

INSERT INTO course (id, name)
Values (1, 'Sem curso'); 