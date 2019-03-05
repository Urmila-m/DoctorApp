<?php
$connectToDatabase=mysqli_connect("localhost", "root", "", "doctorapp") or die('{"res":"database error"}');

$action=(isset($_POST["action"]) ? $_POST["action"] : "") ;
if($action=="insertData"){//email=unique field+primary field

    $name=$_POST["name"];
    $id=(isset($_POST["id"]))?$_POST["id"]:'0';
    $dob=$_POST["dob"];
    $email=$_POST["email"];
    $gender=$_POST["gender"];
    $mobile=(isset($_POST["mobile"]))?$_POST["mobile"]:"";
    $height=(isset($_POST["height"]))?$_POST["height"]:'40';
    $weight=(isset($_POST["weight"]))?$_POST["weight"]:'40';
    $password=(isset($_POST["password"]))?$_POST["password"]:"";
    $image=(isset($_POST["image"]))?$_POST["image"]:"";

    $query="INSERT INTO registration (Name, id, DOB, Gender, Email, MobileNumber, Height, Weight, Password, Image) VALUES ('".$name."','".$id."','".$dob."','".$gender."','".$email."','".$mobile."','".$height."','".$weight."','".$password."','".$image."')";
    $insert=mysqli_query($connectToDatabase, $query);

    if ($insert){
        $response['success']=true;
        $response['errorMsg']="No error";
        echo json_encode($response);
    }

    else{
        $response['success']=false;
        $response['errorMsg']=mysqli_error($connectToDatabase);
        echo json_encode($response);
    }
}