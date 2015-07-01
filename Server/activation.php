<?php
	session_start();
	header("Content-Type: text/html; charset=utf-8");
	include_once("db_connect.php");


	$ip 		= mysqli_real_escape_string($db_conx, $_SERVER['REMOTE_ADDR']) ; 
	$user_id 	= mysqli_real_escape_string($db_conx, htmlspecialchars($_GET['id'], ENT_QUOTES));
	$email 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_GET['email'], ENT_QUOTES));
	$code 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_GET['code'], ENT_QUOTES));

	
	$result = mysqli_query($db_conx," CALL aktivierung('$user_id','$email','$code') ");
		
	if (mysqli_affected_rows($db_conx)) {
		echo "NICE"; 
	}else{
		echo "ERROR"; 
	}
	
	mysqli_close($db_conx);


?>
