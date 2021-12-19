<?php

include 'connection.php';
$response=array();

if(isset($_POST['Email'], $_POST['Pass'])){
    echo $Email=$_POST['Email'];
    
    echo $Pass=$_POST['Pass'];

    $query="SELECT Email  FROM userdata WHERE Email = '$Email' AND Pass = '$Pass'";

    $res=mysqli_query($con, $query);

    if($res->num_rows > 0){

        $response["success"]=1;

        $response["Message"]="Login Success";

    }else{

        $response["success"]=0;

        $response["Message"]="Invalid Credientials";
    }
}
else{

    $response["success"]=0;

    $response["Message"]="Incomplete Data";
}
echo json_encode($response);
?>