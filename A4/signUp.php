<?php

include 'connection.php';
$response=array();

if(isset($_POST['Email'], $_POST['Pass'], $_POST['FirstName'],$_POST['LastName'],$_POST['Gender'],$_POST['Bio'],$_POST['Status'],$_POST['Phone'],$_POST['dp'])){
    echo $Email=$_POST['Email'];
    echo$Pass=$_POST['Pass'];
    echo$FirstName=$_POST['FirstName'];	   
    echo$LastName=$_POST['LastName'];
    echo$Gender=$_POST['Gender'];
    echo$Bio=$_POST['Bio'];	   
    echo$Status=$_POST['Status'];
    echo$Phone=$_POST['Phone'];
    echo$IMG=$_POST['dp'];

    $filename="IMG".rand().".jpg";
	file_put_contents("images/".$filename,base64_decode($IMG));
    echo $filename;
    $qry="INSERT INTO `userdata` (`ID`, `Email`, `Pass`, `FirstName`, `LastName`, `Gender`, `Bio`, `Status`, `Phone`, `IMG`)
    VALUES (NULL,'$Email', '$Pass', '$FirstName', '$LastName',' $Gender', '$Bio', '$Status', '$Phone', '$filename');";
    $res=mysqli_query($con, $qry);

    if($res){
        $response["id"]=mysqli_insert_id($con);
        $response["success"]=1;
    }
    else{
       $response["success"]=0;
       $response["msg"]=mysqli_error($con);
    }
}
else{
    $response["success"]=0;
    $response["msg"]="Incomplete Request";
}

echo json_encode($response);

?>
