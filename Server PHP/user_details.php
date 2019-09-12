<?php
include 'DatabaseConfig.php' ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 $Phone = @$_POST['phone'];

$sql="SELECT `name`, `address`, `pin` FROM `user_registration` WHERE `phone_no`='$Phone'";
$result=mysqli_query($con,$sql);
 
$data=array();

while($row=mysqli_fetch_assoc($result)){
$data["data"][]=$row;

 
}

	header('Content-Type:Application/json');
			
	echo json_encode($data);
	
	
	?>
	


