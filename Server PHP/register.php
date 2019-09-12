<?php

include ("DatabaseConfig.php") ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $name = @$_POST['name'];
 $address = @$_POST['address'];
 $phone = @$_POST['phone'];
 $pin = @$_POST['pin'];



 $Sql_Query = "INSERT INTO `user_registration`(`phone_no`, `name`, `address`, `pin`) VALUES ('$phone','$name','$address','$pin')";
//print_r($Sql_Query); exit();
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Registered Successful!';
 
 }
 else{
 
 echo 'Some Error Occurred!';
 
 }
 mysqli_close($conn);
?>

