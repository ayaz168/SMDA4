<?php

include 'connection.php';
$response=array();

if(isset($_POST['GroupID'])){
    $GroupId=$_POST['GroupID'];
    
    $query1="SELECT * FROM `groupmessage` WHERE GroupID = '$GroupId'";
    $res1=mysqli_query($con, $query1);
    if($res1){
        $response["success"]=1;
        $response["messages"]=array();
        while ($row=mysqli_fetch_array($res1)){
            if($row['MessageImage'] =="0"){
                $messages["MessageType"]="TextMessage";
                $messages["GroupMessageID"]=strval($row["GroupMessageID"]);
                $messages["UserID"]=strval($row["UserID"]);
                $messages["MessageText"]=$row["MessageText"];
                $messages["MessageImage"]="0";
                $messages["MessageRead"]=$row["MessageRead"];
                $messages["MessageHour"]=$row["MessageMin"];
                $messages["MessageMin"]=$row["MessageMin"];
                array_push($response['messages'], $messages);
            }else{
                $messages["MessageType"]="ImageMessage";
                $messages["GroupMessageID"]=strval($row["GroupMessageID"]);
                $messages["UserID"]=strval($row["UserID"]);
                $filename=$row["MessageImage"];
                $myImg=base64_encode(file_get_contents("groupMessages/".$filename)); 
                $messages["MessageImage"]=$myImg;
                $messages["MessageRead"]=$row["MessageRead"];
                $messages["MessageHour"]=$row["MessageMin"];
                $messages["MessageMin"]=$row["MessageMin"];
                array_push($response['messages'], $messages);
            }
            
        }
    }
}else{
    $response["success"]=0;
    $response["message"]="Invalid Data";
}
echo json_encode($response);
?>