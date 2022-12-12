INSERT INTO users(email, encryptedPassword, accessToken, netWorth, roles) 
VALUES('adam@gmail.com', 'abcd1234', 'noaccess', 100, '{"customer"}');
INSERT INTO users(email, encryptedPassword, accessToken, netWorth, roles) 
VALUES('charlie@gmail.com', 'dcba4321', 'noaccess', 2000000, '{"customer", "vip"}');

INSERT INTO games(name, canPlay, minWager, returnData, specialFeatures) 
VALUES('Demon Slayer', 0, 0.50, '{"20%":0.0,"30%":0.5,"40%":1.1,"9%":2.0,"1%":20.0}', '{}');
INSERT INTO games(name, canPlay, minWager, returnData, specialFeatures) 
VALUES('Attack on Titan', 1, 2.50, '{"50%":0.8,"40%":1.1,"10%":1.25}', '{"triple"}');
