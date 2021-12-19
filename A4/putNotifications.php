<?php

include 'connection.php';
$response=array();

if(isset($_POST['Email'], $_POST['DeviceID'], $_POST['Message'], $_POST['TimeSlot'])){
        $Email=$_POST['Email'];
        $DeviceID=$_POST['DeviceID'];
        $Message=$_POST['Message'];	   
        $TimeSlot=$_POST['TimeSlot'];
        $qry="INSERT INTO `mynotifications` (`ID`, `Email`, `DeviceID`, `Message`, `TimeSlot`) VALUES (NULL, '$Email', '$DeviceID', '$Message', '$TimeSlot');";
        $res=mysqli_query($con, $qry);
        if($res){
            $response["id"]=mysqli_insert_id($con);
            $response["success"]=1;
        }
        else{
        $response["success"]=0;
        $response["msg"]=mysqli_error($con);
        }

}else{
    $response["success"]=0;
    $response["msg"]="Incomplete Request";
}
    
echo json_encode($response);

?>
