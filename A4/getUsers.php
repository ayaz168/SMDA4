<?php

include 'connection.php';


$query="SELECT * FROM `userdata` ORDER BY `userdata`.`id` ASC";
$res=mysqli_query($con, $query);

$response=array();

if($res){
    $response["success"]=1;
    $response["users"]=array();
    while ($row=mysqli_fetch_array($res)){
        $users["ID"]=$row["ID"];
        $users["Email"]=$row["Email"];
        $users["Pass"]=$row["Pass"];
        $users["FirstName"]=$row["FirstName"];
        $users["LastName"]=$row["LastName"];
        $users["Gender"]=$row["Gender"];
        $users["Bio"]=$row["Bio"];
        $users["Status"]=$row["Status"];
        $users["Phone"]=$row["Phone"];
        $filename=$row["IMG"];
        $myImg=base64_encode(file_get_contents("images/".$filename)); 
        $users["dp"]=$myImg;
        array_push($response['users'], $users);
    }
}
else{
    $response["success"]=0;
}
echo json_encode($response);
?>