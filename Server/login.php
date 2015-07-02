<?php
	header("Content-Type: text/html; charset=utf-8");
	include_once("db_connect.php");

	$ip 		= mysqli_real_escape_string($db_conx, $_SERVER['REMOTE_ADDR']) ; 

	$email 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['email'], ENT_QUOTES));
	$passwort 	= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['password'], ENT_QUOTES));
	$result = 	mysqli_query($db_conx," CALL FINANZLogin('$email','$passwort','$ip') ");
	$result_array = mysqli_fetch_array( $result, MYSQL_ASSOC);

	echo json_encode($result_array); 

	mysqli_close($db_conx);

?>
