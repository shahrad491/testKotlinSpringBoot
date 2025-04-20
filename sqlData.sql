INSERT INTO "authors" (
  "id", "age", "description", "image",
  "name"
)
VALUES
  (
    1, 40, 'testUser1-Desc' ,'testUser1.png',
    'test user1'
  ),
  (
    2, 35, 'testUser2-Desc', 'testUser2.png',
    'test user2'
  ),
  (
    3, 45, 'testUser3-Desc', 'testUser3.png',
    'test user3'
  ),
  (
    4, 44, 'testUser4-Desc', 'testUser4.png',
    'test user4'
  ),
  (
    5, 50, 'testUser5-Desc' ,'testUser5.png',
    'test user5'
  ),
  (
    6, 46, 'testUser6-Desc', 'testUser6.png',
    'test user6'
  ),
  (
    7, 47, 'testUser7-Desc' ,'testUser7.png',
    'test user7'
  ),
  (
    8, 48, 'testUser8-Desc', 'testUser8.png',
    'test user8'
  ),
  (
    9, 49, 'testUser9-Desc', 'testUser9.png',
    'test user9'
  ),
  (
    10, 20, 'testUser10-Desc', 'testUser10.png',
    'test user10'
  );
INSERT INTO "books" (
  "isbn", "description", "image", "title",
  "author_id"
)
VALUES
  (
    '123', 'testBook123',
    'testBook123.png', 'testBook123',
    5
  ),
  (
    '1234', 'testBook1234',
    'testBook1234.png', 'testBook1234',
    1
  ),
  (
    '12345', 'testBook12345',
    'testBook12345.png', 'testBook12345',
    2
  ),
  (
    '123456', 'testBook123456',
    'testBook123456.png', 'testBook123456',
    3
  ),
  (
    '1234567', 'testBook1234567',
    'testBook1234567.png', 'testBook1234567',
    4
  ),
  (
    '12345678', 'testBook12345678',
    'testBook12345678.png', 'testBook12345678',
    6
  ),
  (
    '123456789', 'testBook123456789',
    'testBook123456789.png', 'testBook123456789',
    7
  ),
  (
    '123456890', 'testBook1234567890',
    'testBook1234567890.png', 'testBook1234567890',
    8
  );

