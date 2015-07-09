<?php
	header("Content-Type: text/html; charset=utf-8");
	include_once("db_connect.php");

	$ip 		= mysqli_real_escape_string($db_conx, $_SERVER['REMOTE_ADDR']) ; 

	$email 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['email'], ENT_QUOTES));

	$array_array = mysqli_fetch_array( mysqli_query($db_conx," CALL FINANZgetUser('$email') ") , MYSQL_ASSOC);
	
	echo json_encode($array_array); 

	mysqli_close($db_conx);

?>
