<?php
include_once("db_connect.php");

$sql = "CREATE TABLE IF NOT EXISTS user (
              _id INT UNSIGNED NOT NULL AUTO_INCREMENT,
			  name VARCHAR(255) NOT NULL,
			  email VARCHAR(255) NOT NULL,
			  passwort VARCHAR(255) NOT NULL,
			  ip VARCHAR(255) NOT NULL,
			  online TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
			  aktiviert VARCHAR(255) NOT NULL,
			  
              PRIMARY KEY (_id),
	      		  UNIQUE KEY (email)
             )ENGINE=InnoDB DEFAULT CHARSET=utf8;";

sql_funktion($sql,$db_conx);


$sql = "CREATE TABLE IF NOT EXISTS gruppe (
              _id INT UNSIGNED NOT NULL AUTO_INCREMENT,
			  name VARCHAR(255) NOT NULL,
			  user_id INT UNSIGNED NOT NULL,
              PRIMARY KEY (_id),
			  FOREIGN KEY (user_id) REFERENCES user(_id)
					ON DELETE CASCADE
					ON UPDATE CASCADE
             )ENGINE=InnoDB DEFAULT CHARSET=utf8;";

sql_funktion($sql,$db_conx);

$sql = "CREATE TABLE IF NOT EXISTS ausgabe (
			  _id INT UNSIGNED NOT NULL AUTO_INCREMENT,
			  was VARCHAR(255) NOT NULL,
			  datum DATE NOT NULL,
			  betrag FLOAT NOT NULL,
			  user_id INT UNSIGNED NOT NULL,
			  gruppe_id INT UNSIGNED NOT NULL,
              PRIMARY KEY (_id),
	      	  FOREIGN KEY (user_id) REFERENCES user(_id)
				ON DELETE CASCADE
				ON UPDATE CASCADE,
			  FOREIGN KEY (gruppe_id) REFERENCES gruppe(_id)
				ON DELETE CASCADE
				ON UPDATE CASCADE
             )ENGINE=InnoDB DEFAULT CHARSET=utf8;";

sql_funktion($sql,$db_conx);

$sql = "CREATE TABLE IF NOT EXISTS user_ist_mitglied_in_gruppe (
			  user_id INT UNSIGNED NOT NULL,
			  gruppe_id INT UNSIGNED NOT NULL,
              PRIMARY KEY (user_id, gruppe_id),
	      	  FOREIGN KEY (user_id) REFERENCES user(_id)
				ON DELETE CASCADE
				ON UPDATE CASCADE,
			  FOREIGN KEY (gruppe_id) REFERENCES gruppe(_id)
				ON DELETE CASCADE
				ON UPDATE CASCADE
             )ENGINE=InnoDB DEFAULT CHARSET=utf8;";

sql_funktion($sql,$db_conx);


function sql_funktion ($sql, $db_conx)
{

	if (!mysqli_query($db_conx, "SET FOREIGN_KEY_CHECKS = 0;")) {
    		printf("Error: %s\n", mysqli_sqlstate($db_conx));
	}
	if (!mysqli_query($db_conx, $sql)) {
    		printf("Error: %s\n", mysqli_sqlstate($db_conx));
		echo "<h3>".$sql." table NOT created :( </h3>";
	}else {
		echo "<h3>table created :) </h3>";  
	}
	if (!mysqli_query($db_conx, "SET FOREIGN_KEY_CHECKS = 1;")) {
    		printf("Error: %s\n", mysqli_sqlstate($db_conx));
	}
}
?>
