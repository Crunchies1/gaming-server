DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id serial PRIMARY KEY, 
    email VARCHAR(255), 
    encryptedPassword VARCHAR(255), 
    accessToken VARCHAR(32),
    netWorth NUMERIC,
    roles VARCHAR(255)
);

DROP TABLE IF EXISTS games;

CREATE TABLE games(
    id serial PRIMARY KEY, 
    name VARCHAR(255), 
    canPlay boolean, 
    minWager NUMERIC,
    returnData VARCHAR(255),
    specialFeatures VARCHAR(255)
);
