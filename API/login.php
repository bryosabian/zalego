<?php 
include_once "config.php";

$params=$_POST;
$res=[];
$email=$params["email"];
$password=$params["password"];

if(!filter_var($email,FILTER_VALIDATE_EMAIL)){
$res["success"]=false;
$res["message"]="Invalid email address";
echo json_encode($res);
exit();
}

$statement = mysqli_prepare($con,'select * from users where email=?');
     $statement->bind_param("s",$email);
     $statement->execute();
     $statement->store_result();
     if ($statement->num_rows > 0) {
          $statement->bind_result($rid,$rfirstname,$remail,$rgender,$rlanguage,$rdob,$rpassword);
          $statement->fetch();
		  $rows=true;
     }

if(!$rows){
$res["success"]=false;
$res["message"]="Email does not exist";
echo json_encode($res);
exit();
}

if(md5($password)!=$rpassword){
$res["success"]=false;
$res["message"]="Invalid password";
echo json_encode($res);
exit();
}

$res["data"]=$rows;
$res["success"]=true;
echo json_encode($res);
exit();
?>