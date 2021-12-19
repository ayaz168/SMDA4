<?php

include 'connection.php';
$response=array();

if(isset($_POST['SenderID'], $_POST['RecieverID'], $_POST['MessageHour'], $_POST['MessageMin'], $_POST['MessageRead'], $_POST['MessageSafe'])){
    if($_POST['MessageType']=="TextMessage" && isset($_POST['MessageText']) ){
        $SenderID=$_POST['SenderID'];
        $RecieverID=$_POST['RecieverID'];
        $MessageHour=$_POST['MessageHour'];	   
        $MessageMin=$_POST['MessageMin'];
        $MessageText=$_POST['MessageText'];
        $MessageRead=$_POST['MessageRead'];	   
        $MessageSafe=$_POST['MessageSafe'];
        $qry="INSERT INTO  `usermessage` (`MessageID`, `SenderID`, `RecieverID`, `MessageHour`, `MessageMin`, `MessageText`, `MessageImage`, `MessageRead`, `MessageSafe`)
        VALUES (NULL, '$SenderID', '$RecieverID', '$MessageHour', '$MessageMin', '$MessageText', '0', '$MessageRead', '$MessageSafe');";
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
        $SenderID=$_POST['SenderID'];
        $RecieverID=$_POST['RecieverID'];
        $MessageHour=$_POST['MessageHour'];	   
        $MessageMin=$_POST['MessageMin'];
        $MessageImage=$_POST['MessageImage'];
        $MessageRead=$_POST['MessageRead'];	   
        $MessageSafe=$_POST['MessageSafe'];

        $filename="IMGMSG".rand().".jpg";
        file_put_contents("messageImages/".$filename,base64_decode($MessageImage));
        echo $filename;
        $qry="INSERT INTO  `usermessage` (`MessageID`, `SenderID`, `RecieverID`, `MessageHour`, `MessageMin`, `MessageText`, `MessageImage`, `MessageRead`, `MessageSafe`)
        VALUES (NULL, '$SenderID', '$RecieverID', '$MessageHour', '$MessageMin', '0', '$filename', '$MessageRead', '$MessageSafe');";
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
