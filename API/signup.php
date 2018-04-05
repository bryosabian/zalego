<?php 
include_once "config.php";

$params=$_POST;
$res=[];
$email=$params["email"];
$password=$params["password"];
$firstname=$params["firstname"];
$gender=$params["gender"];
$language=$params["language"];
$dob=$params["dob"];

if(!filter_var($email,FILTER_VALIDATE_EMAIL)){
$res["success"]=false;
$res["message"]="Invalid email address";
echo json_encode($res);
exit();
}



$password=md5($password);
$stmt=mysqli_prepare($con,"insert into users(firstname,email,gender,language,dob,password) values(?,?,?,?,?,?)");
mysqli_stmt_bind_param($stmt,"ssssss",$firstname,$email,$gender,$language,$dob,$password);
$ex=mysqli_stmt_execute($stmt);

if(!$ex){
$res["success"]=false;
$res["message"]="Database error ".mysqli_error($con);
echo json_encode($res);
exit();
}


$res["success"]=true;
$res["message"]="Data saved";
echo json_encode($res);
exit();

?>