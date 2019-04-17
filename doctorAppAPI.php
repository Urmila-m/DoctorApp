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

    $query="INSERT INTO registration (Name, id, DOB, Gender, Email, MobileNumber, Height, Weight, Password, Image, BloodGroup) VALUES ('".$name."','".$id."','".$dob."','".$gender."','".$email."','".$mobile."','".$height."','".$weight."','".$password."','".$image."', 'not set yet')";
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

else if($action=="getEmailList"){
    $query="SELECT Email, Password FROM registration";
    $getEmail=mysqli_query($connectToDatabase, $query);
    if ($getEmail) {
        $dataArray = array();
        while ($rows = mysqli_fetch_array($getEmail)) {
            $reqList['Email'] = $rows[0];
            $reqList['Password'] = $rows[1];
            array_push($dataArray, $reqList);
        }
        echo json_encode($dataArray);
    }
    else{
        echo mysqli_error($connectToDatabase);
    }
}

else if ($action=="getRecordWithEmail"){
    $email=$_POST["email"];
    $query="SELECT * FROM registration WHERE Email='".$email."'";
    $getRecord=mysqli_query($connectToDatabase, $query);
    if ($getRecord==false){
        echo mysqli_error($connectToDatabase);
    }
    else {
        $rows = mysqli_fetch_array($getRecord);
        $required['id'] = $rows[0];
        $required['Name'] = $rows[1];
        $required['DOB'] = $rows[2];
        $required['Gender'] = $rows[3];
        $required['Email'] = $rows[4];
        $required['MobileNumber'] = $rows[5];
        $required['Height'] = $rows[6];
        $required['Weight'] = $rows[7];
        $required['Password'] = $rows[8];
        $required['Image'] = $rows[9];
		$required['BloodGroup']=$rows[10];
        echo json_encode($required);
    }

}

else if($action=="getDoctorList"){
    $query="SELECT * FROM doctor";
    $getDoctorList=mysqli_query($connectToDatabase, $query);
    if ($getDoctorList){
        $reqList=array();
        while ($rows=mysqli_fetch_array($getDoctorList)){
            $required["id"]=$rows[0];
            $required["name"]=$rows[1];
            $required["hospital"]=$rows[2];
            $required["rating"]=$rows[3];
            $required["image"]=$rows[4];
            $required["speciality"]=$rows[5];
            $required["phone"]=$rows[6];
            $required["fee"]=$rows[7];

            array_push($reqList, $required);
        }

        echo json_encode($reqList);

    }
}

else if($action=="editProfile") {
    $weight = $_POST['weight'];
    $height = $_POST['height'];
    $blood = $_POST['blood'];
    $email=$_POST['email'];
    $query = 'UPDATE registration SET Weight="' . $weight . '", Height="' . $height . '", BloodGroup="' . $blood .'" WHERE Email="' . $email . '"';
    echo $query;
    $update = mysqli_query($connectToDatabase, $query);
    if ($update) {
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

else if($action=="getIdList"){
    $query="SELECT id FROM registration";
    $getId=mysqli_query($connectToDatabase, $query);
    if ($getId) {
        $dataArray = array();
        while ($rows = mysqli_fetch_array($getId)) {
            $reqList['id'] = $rows[0];
            array_push($dataArray, $reqList);
        }
        echo json_encode($dataArray);
    }
    else{
        echo mysqli_error($connectToDatabase);
    }
}

else if ($action=="getRecordWithId"){
    $id=$_POST["id"];
    $query="SELECT * FROM registration WHERE id='".$id."'";
    $getRecord=mysqli_query($connectToDatabase, $query);
    if ($getRecord==false){
        echo mysqli_error($connectToDatabase);
    }
    else {
        $rows = mysqli_fetch_array($getRecord);
        $required['id'] = $rows[0];
        $required['Name'] = $rows[1];
        $required['DOB'] = $rows[2];
        $required['Gender'] = $rows[3];
        $required['Email'] = $rows[4];
        $required['MobileNumber'] = $rows[5];
        $required['Height'] = $rows[6];
        $required['Weight'] = $rows[7];
        $required['Password'] = $rows[8];
        $required['Image'] = $rows[9];
		$required['BloodGroup']=$rows[10];
        echo json_encode($required);
    }
}

else if ($action=="setAppointment"){
    $doctor=$_POST["doctorName"];
    $doctorFee=$_POST["doctorFee"];
    $appDate=$_POST["appointment_date"];
    $appTime=$_POST["appointment_time"];
    $patient=$_POST["patient"];
    $query="INSERT INTO appointment (Doctor, Patient, Date, Time, Fee) VALUES ('".$doctor."', '".$patient."', '".$appDate."', '".$appTime."', '".$doctorFee."')";
    $setApp=mysqli_query($connectToDatabase, $query);
    if ($setApp){
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

else if ($action=="getAppDetails"){
    $patient=$_POST["patientName"];
    $query="SELECT Doctor, Date, Time FROM appointment WHERE Patient='".$patient."'";
    $DoctorAndDate=mysqli_query($connectToDatabase, $query);
    if ($DoctorAndDate){
        $dataArray=array();
        while ($rows=mysqli_fetch_array($DoctorAndDate)){
            $required["doctor"]=$rows[0];
            $required["appointment_date"]=$rows[1];
            $required["appointment_time"]=$rows[2];
            array_push($dataArray, $required);
        }
        echo json_encode($dataArray);
    }
}

?>