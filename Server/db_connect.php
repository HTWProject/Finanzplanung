<?php
$db_conx = mysqli_connect("cgi.rz.htw-berlin.de","s0539589","Kis-Kija","s0539589");		

	

if (mysqli_connect_errno()) {
    echo mysqli_connect_error();
    exit();
} else {
	if (!mysqli_query($db_conx, "SET CHARACTER SET utf8")) {
    		printf("Error: %s\n", mysqli_sqlstate($db_conx));
	}
}
?>
