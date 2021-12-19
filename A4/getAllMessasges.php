<?php

include 'connection.php';
$response=array();

if(isset($_POST['SenderID'],$_POST['RecieverID'])){
    $sender=$_POST['SenderID'];
    $rec=$_POST['RecieverID'];
    $query="SELECT * FROM `usermessage` WHERE SenderID = '$sender' AND RecieverID = '$rec'";

    //$query="SELECT * FROM `usermessage` ORDER BY `usermessage`.`messageid` ASC";
    $res=mysqli_query($con, $query);
    
    
    if($res){
        $response["success"]=1;
        $response["messages"]=array();
        while ($row=mysqli_fetch_array($res)){
            if($row["MessageImage"]=='0'){
                $messages["MessageType"]="TextMessage";
                $messages["MessageID"]=strval($row["MessageID"]);
                $messages["SenderID"]=strval($row["SenderID"]);
                $messages["RecieverID"]=strval($row["RecieverID"]);
                $messages["MessageHour"]=$row["MessageHour"];
                $messages["MessageMin"]=$row["MessageMin"];
                $messages["MessageText"]=$row["MessageText"];
                $messages["MessageImage"]="0";
                $messages["MessageRead"]=$row["MessageRead"];
                $messages["MessageSafe"]=$row["MessageSafe"];
                array_push($response['messages'], $messages);
            }else{
                $messages["MessageType"]="ImageMessage";
                $messages["MessageID"]=strval($row["MessageID"]);
                $messages["SenderID"]=strval($row["SenderID"]);
                $messages["RecieverID"]=strval($row["RecieverID"]);
                $messages["MessageHour"]=$row["MessageHour"];
                $messages["MessageMin"]=$row["MessageMin"];
                $messages["MessageText"]="0";
    //            $messages["Phone"]=$row["MessageImage"];
                $messages["MessageRead"]=$row["MessageRead"];
                $messages["MessageSafe"]=$row["MessageSafe"];
                $filename=$row["MessageImage"];
                $myImg=base64_encode(file_get_contents("messageImages/".$filename)); 
                $messages["MessageImage"]=$myImg;
                array_push($response['messages'], $messages);
            }
        }
        //Reciever to sender
        $query2="SELECT * FROM `usermessage` WHERE SenderID = '$rec' AND RecieverID = '$sender'";

        //$query="SELECT * FROM `usermessage` ORDER BY `usermessage`.`messageid` ASC";
        $res2=mysqli_query($con, $query2);
        if($res2){
            while ($row=mysqli_fetch_array($res2)){
                if($row["MessageImage"]=='0'){
                    $messages["MessageType"]="TextMessage";
                    $messages["MessageID"]=$row["MessageID"];
                    $messages["SenderID"]=$row["SenderID"];
                    $messages["RecieverID"]=$row["RecieverID"];
                    $messages["MessageHour"]=$row["MessageHour"];
                    $messages["MessageMin"]=$row["MessageMin"];
                    $messages["MessageText"]=$row["MessageText"];
                    $messages["MessageImage"]="0";
                    $messages["MessageRead"]=$row["MessageRead"];
                    $messages["MessageSafe"]=$row["MessageSafe"];
                    array_push($response['messages'], $messages);
                }else{
                    $messages["MessageType"]="ImageMessage";
                    $messages["MessageID"]=$row["MessageID"];
                    $messages["SenderID"]=$row["SenderID"];
                    $messages["RecieverID"]=$row["RecieverID"];
                    $messages["MessageHour"]=$row["MessageHour"];
                    $messages["MessageMin"]=$row["MessageMin"];
                    $messages["MessageText"]="0";
        //            $messages["Phone"]=$row["MessageImage"];
                    $messages["MessageRead"]=$row["MessageRead"];
                    $messages["MessageSafe"]=$row["MessageSafe"];
                    $filename=$row["MessageImage"];
                    $myImg=base64_encode(file_get_contents("messageImages/".$filename)); 
                    $messages["MessageImage"]=$myImg;
                    array_push($response['messages'], $messages);
                }
            }
        }
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