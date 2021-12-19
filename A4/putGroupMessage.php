<?php

include 'connection.php';
$response=array();

if(isset($_POST['GroupID'], $_POST['UserID'], $_POST['MessageRead'], $_POST['MessageHour'], $_POST['MessageMin'])){
    if($_POST['MessageType']=="TextMessage" && isset($_POST['MessageText']) ){
        $GroupID=$_POST['GroupID'];
        $UserID=$_POST['UserID'];
        $MessageText=$_POST['MessageText'];
        $MessageRead=$_POST['MessageRead'];	
        $MessageHour=$_POST['MessageHour'];	   
        $MessageMin=$_POST['MessageMin'];   
        $qry="INSERT INTO  `groupmessage` (`GroupMessageID`, `GroupID`, `UserID`, `MessageText`, `MessageImage`, `MessageRead`, `MessageHour`, `MessageMin`)
        VALUES (NULL, '$GroupID', '$UserID', '$MessageText', '0','$MessageRead', '$MessageHour', '$MessageMin');";
        $res=mysqli_query($con, $qry);

        if($res){
            $response["id"]=mysqli_insert_id($con);
            $response["success"]=1;
        }
        else{
        $response["success"]=0;
        $response["msg"]=mysqli_error($con);
        }

    }else if($_POST['MessageType']=="ImageMessage"&& isset($_POST['MessageImage'])){
        $GroupID=$_POST['GroupID'];
        $UserID=$_POST['UserID'];
        $MessageImage=$_POST['MessageImage'];
        $filename="IMGMSG".rand().".jpg";
        file_put_contents("groupMessages/".$filename,base64_decode($MessageImage));
        $MessageRead=$_POST['MessageRead'];	
        $MessageHour=$_POST['MessageHour'];	   
        $MessageMin=$_POST['MessageMin'];   
        $qry="INSERT INTO  `groupmessage` (`GroupMessageID`, `GroupID`, `UserID`, `MessageText`, `MessageImage`, `MessageRead`, `MessageHour`, `MessageMin`)
        VALUES (NULL, '$GroupID', '$UserID', '0', '$filename','$MessageRead', '$MessageHour', '$MessageMin');";
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
        $response["msg"]="Invalid/Incomplete Message Type/Data";
    }

}else{
    $response["success"]=0;
    $response["msg"]="Incomplete Request";
}
    
echo json_encode($response);

?>
