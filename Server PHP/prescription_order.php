<?php
include 'DatabaseConfig.php' ;
 $conn = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
		
		$image = $_POST['image'];
        $phone = $_POST['phone'];
        $name = $_POST['name'];
        $phoneno = $_POST['phoneno'];
        $address = $_POST['address'];
        $pin = $_POST['pin'];
        $description = $_POST['description'];
       

date_default_timezone_set('Asia/Kolkata');
 $current_date = date('d/m/Y H:i:s');
		//echo "hello";

// 		$query="SELECT `address` FROM `user_registration` WHERE `phone_no`='8777588314'";
// 		$result=mysqli_query($conn,$query);
// 		if (mysqli_num_rows($result) > 0) {
//         while($row = mysqli_fetch_assoc($result)){
//             $address = $row["address"];
//         }
// 		}
		
// 		$query="SELECT `pin` FROM `user_registration` WHERE `phone_no`='8777588314'";
// 		$result=mysqli_query($conn,$query);
// 		if (mysqli_num_rows($result) > 0) {
//         while($row = mysqli_fetch_assoc($result)){
//             $pin = $row["pin"];
//         }
// 		}

		
		$path = "uploads/order_by_prescription/$phone.png";
		$actualpath = "https://raymedicinecorner.com/$path";
		$sql = "INSERT INTO `order_by_prescription`(`user_phone`, `prescription_image`, `address`, `pin`, `order_time`) VALUES ('$phoneno','$actualpath','$address','$pin','$current_date')";
		//print_r($sql); exit;
		
		if(mysqli_query($conn,$sql)){
			file_put_contents($path,base64_decode($image));
			echo "Your Order is Placed";
		}
		else{
		echo "Error";
	}
	?>


