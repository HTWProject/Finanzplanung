<?php
	header("Content-Type: text/html; charset=utf-8");
	include_once("db_connect.php");


	$ip 		= mysqli_real_escape_string($db_conx, $_SERVER['REMOTE_ADDR']) ; 
	$name 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['name'], ENT_QUOTES));
	$email 		= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['email'], ENT_QUOTES));
	$passwort 	= mysqli_real_escape_string($db_conx, htmlspecialchars($_POST['password'], ENT_QUOTES));
	$code 		= mt_rand(1988, 2000015); 


	
	$result = mysqli_query($db_conx," CALL registration('$email','$name','$passwort','$ip', '$code') ");
	$result_array = mysqli_fetch_array($result, MYSQL_ASSOC);
	
	if ($result_array['_id'] != NULL) {
		
	    	email($email,$result_array['_id'],$code,$passwort,$name);
		
	}

	echo json_encode($result_array); 
	
	mysqli_close($db_conx);




	function email($email,$id,$code, $pass, $name){
		$sender = "admin@fomenko.eu";
		$empfaenger = "$email";
		$betreff = "Your Activation Link:";
		$link = "http://home.htw-berlin.de/~s0539589/API/activation.php?id=" . urlencode($id) . "&email=" . urlencode($email) . "&code=$code";
		$mailtext = "Hello $name!<br>Your Password: $pass<br><b>Activation Code: $code</b><b>Activation Link:</b> <a href='$link'> Link Click Here </a> ";
		mail($empfaenger, $betreff, $mailtext, "From: $sender\n" . "Content-Type: text/html; charset=utf-8\n"); 
	}

?>
