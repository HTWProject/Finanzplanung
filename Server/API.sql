
DROP PROCEDURE IF EXISTS s0539589.FINANZregistration;
delimiter //
CREATE PROCEDURE s0539589.FINANZregistration(IN $email VARCHAR(255), IN $name VARCHAR(255), IN $passwort VARCHAR(255), IN $ip VARCHAR(255), IN $aktiviert VARCHAR(255))
BEGIN
	IF ($email NOT REGEXP '^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$' OR $email NOT LIKE '%_@_%._%') THEN
		SELECT ("Registration was not sucessfulf, write your email correct") AS exception;
	ELSEIF ($passwort = ' ' AND $passwort = '') THEN
		SELECT ("Registration was not sucessfulf, write your password") AS exception;
	ELSEIF ($name = ' ' AND $name = '' ) THEN
		SELECT ("Registration was not sucessfulf, write your name") AS exception;
	ELSEIF EXISTS (SELECT * FROM user WHERE email = TRIM($email)) then
		SELECT ("Registration was not sucessfulf, try other email because there are a user with this email") AS exception;
	ELSE 
		INSERT INTO user(`email`,`name`,`passwort`,`ip`, `aktiviert`) values(TRIM($email),$name,TRIM($passwort),$ip,$aktiviert);
		SELECT ("Registration was sucessfull, Check your eMail Adress fot the Activationslink") AS exception, last_insert_id() AS _id;
	END IF;
END//
delimiter ;


DROP PROCEDURE IF EXISTS s0539589.FINANZaktivierung;
delimiter //
CREATE PROCEDURE s0539589.FINANZaktivierung(IN $account_id INT UNSIGNED, IN $email VARCHAR(255), IN $aktiviert VARCHAR(255))
BEGIN
	IF EXISTS(SELECT * FROM user WHERE _id=$account_id AND aktiviert = $aktiviert AND email = $email) THEN
		UPDATE user SET aktiviert='1' WHERE _id=$account_id;
	END IF;
END//
delimiter ;


DROP PROCEDURE IF EXISTS s0539589.FINANZLogin;
delimiter //
CREATE PROCEDURE s0539589.FINANZLogin(IN $email VARCHAR(255),IN $passwort VARCHAR(255),IN $ip VARCHAR(255))
BEGIN
	IF EXISTS(SELECT * FROM user WHERE aktiviert = 1 AND email = $email AND passwort = $passwort) THEN
		UPDATE user SET `online` = CURRENT_TIMESTAMP, `ip` = $ip  WHERE email = $email AND passwort = $passwort;
		SELECT _id, name, email, ("OK") AS exception FROM user WHERE email = $email AND passwort = $passwort;
	ELSEIF EXISTS(SELECT * FROM user WHERE aktiviert != 1 AND email = $email AND passwort = $passwort) THEN
		SELECT ("Account noch nicht Activiert") AS exception;
	ELSE 
		SELECT ("Falsche Eingabe") AS exception;
	END IF;
END//
delimiter ;



DROP PROCEDURE IF EXISTS s0539589.FINANZLoginFail;
delimiter //
CREATE PROCEDURE s0539589.FINANZLoginFail(IN $email VARCHAR(255))
BEGIN
	SELECT name, email, passwort FROM user WHERE email = $email AND aktiviert = '1';
END//
delimiter ;

DROP PROCEDURE IF EXISTS s0539589.FINANZgetLogin;
delimiter //
CREATE PROCEDURE s0539589.FINANZgetLogin(IN $account_id INT UNSIGNED, IN $ip VARCHAR(255))
BEGIN
	UPDATE user SET `online` = CURRENT_TIMESTAMP, `ip` = $ip  WHERE _id = $account_id;
	SELECT _id, name, email, aktiviert FROM user WHERE _id = $account_id;
END//
delimiter ;



DROP PROCEDURE IF EXISTS s0539589.FINANZgetPasswort;
delimiter //
CREATE PROCEDURE s0539589.FINANZgetPasswort(IN $email VARCHAR(255))
BEGIN
	SELECT passwort FROM user WHERE email = $email;
END//
delimiter ;


DROP PROCEDURE IF EXISTS s0539589.FINANZsetPasswort;
delimiter //
CREATE PROCEDURE s0539589.FINANZsetPasswort(IN $account_id INT UNSIGNED, IN $oldpasswort VARCHAR(255), IN $newpasswort VARCHAR(255), IN $ip VARCHAR(255))
BEGIN
	UPDATE user SET `online`= CURRENT_TIMESTAMP, `ip` = $ip, `passwort` = $newpasswort WHERE `_id` = $account_id AND `passwort` = $oldpasswort;
END//
delimiter ;



