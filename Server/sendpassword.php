<?php
	session_start();
	header("Content-Type: text/html; charset=utf-8");
	include_once("db_connect.php");


	$email 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['email'], ENT_QUOTES));

	$result = mysqli_query($db_conx," CALL LoginFail('$email')");

	if (mysqli_num_rows($result)>0) {
		$passwort_array = mysqli_fetch_array( $result , MYSQL_ASSOC);
    		email($passwort_array['email'], $passwort_array['name'], $passwort_array['passwort']);
		echo $passwort_array['email'];
	}
	mysqli_close($db_conx);


	function email($email, $name, $pass){
		$sender = "admin@fomenko.eu";
		$empfaenger = "$email";
		$betreff = "Your Password:";
		$mailtext = "Hello $name!<br>Your Password: $pass";
		mail($empfaenger, $betreff, $mailtext, "From: $sender\n" . "Content-Type: text/html; charset=utf-8\n"); 
	}

?>