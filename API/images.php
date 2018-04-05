<?php 
include_once "config.php";
$rows=[];
$statement = mysqli_query($con,'select * from images');
     $result=$statement;
          
          while ($row = $result->fetch_assoc()) {
              $rows[]=$row;
}
     


$res["data"]=$rows;
$res["success"]=true;
echo json_encode($rows);
exit();
?>