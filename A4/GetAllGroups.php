<?php

include 'connection.php';
$response=array();

if(isset($_POST['UserID'])){
    $UserId=$_POST['UserID'];
    
    $query1="SELECT * FROM `groupparticipants` WHERE UserID = '$UserId'";

    $res1=mysqli_query($con, $query1);
    if($res1){
        $response["success"]=1;
        $response["groups"]=array();
        while ($row=mysqli_fetch_array($res1)){
            $usI=$row['GroupID'];
            $query2="SELECT * FROM `groupx` WHERE GroupId = '$usI'";
            $res2=mysqli_query($con, $query2);
            if($res2){
                while ($row2=mysqli_fetch_array($res2)){
                    $groups["GroupId"]=$row2["GroupId"];
                    $groups["GroupName"]=$row2["GroupName"];
                    array_push($response['groups'], $groups);
                }
            }
        }
    }
}else{
    $response["success"]=0;
    $response["message"]="Invalid Data";
}
echo json_encode($response);
?>