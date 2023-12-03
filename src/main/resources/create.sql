CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    token VARCHAR(255),
    active BOOLEAN
    );

CREATE TABLE phones (
    id UUID PRIMARY KEY,
    number VARCHAR(255),
    citycode VARCHAR(10),
    countrycode VARCHAR(10),
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE users_phones (
      user_id UUID,
      phone_id UUID,
      PRIMARY KEY (user_id, phone_id),
      FOREIGN KEY (user_id) REFERENCES users(id),
      FOREIGN KEY (phone_id) REFERENCES phones(id)
);

