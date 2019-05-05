<?php

    $connectToDatabase=mysqli_connect("localhost", "root", "", "doctorapp") or die('{"res":"database error"}');

    $action=(isset($_POST["action"])?($_POST["action"]):"");

    if ($action=="uploadImage"){
        $image=$_POST["image"];
        $email=$_POST["email"];
        $today=$_POST["today"];
        $imageName=$_POST["imageName"];
        $requiredEmail=$_POST["requiredEmail"];

        $fileDir="imageUpload/".$requiredEmail;
        if (!file_exists('"'.$fileDir.'"')){
            mkdir($fileDir, 0755);
        }
        $upload_path="imageUpload/".$requiredEmail."/".$imageName.".png";
        $finalImageName='http://192.168.1.72/'.$upload_path;

        $createTableQuery="CREATE TABLE IF NOT EXISTS `doctorapp`.`".$email."` ( `id` INT NOT NULL AUTO_INCREMENT , `Image` TEXT NOT NULL , `Date` TEXT NOT NULL , PRIMARY KEY (`id`))";
        $createTable=mysqli_query($connectToDatabase, $createTableQuery);;

        $query="INSERT INTO `".$email."` (`Image`, `Date`) VALUES ('".$finalImageName."', '".$today."')";
        $upload=mysqli_query($connectToDatabase, $query);
        if ($upload) {
            file_put_contents($upload_path, base64_decode($image));
            $response = "No error";
            echo json_encode(array('response'=>$response));
        }
        else{
            $response=mysqli_error($connectToDatabase);
            echo json_encode(array('response'=>$response));
        }
    }

    else if ($action=="getAllImages"){
        $email=$_POST["email"];
        $query="SELECT Image FROM `".$email."`";
        $select=mysqli_query($connectToDatabase, $query);
        $response=array();
        if ($select){
            while ($rows=mysqli_fetch_array($select)){
                $required["ImageUrl"]=$rows[0];
                array_push($response, $required);
            }
        }
        else{
            $required["ImageUrl"]= mysqli_error($connectToDatabase);
            array_push($response, $required);
        }

        echo json_encode($response);
    }

    mysqli_close($connectToDatabase);

?>