<?php
    $connectToDatabase=mysqli_connect("localhost", "root", "", "doctorapp") or die('{"res":"database error"}');

    $action=(isset($_POST["action"])?($_POST["action"]):"");
//    $SERVER_IMAGE_FOLDER_URL="http://192.168.1.72/imageUpload/".."";
    $upload_path="imageUpload/urmila.png";

    if ($action=="uploadImage"){
        $image=$_POST["image"];
//        $email=$_POST["email"];
//        $createTableQuery="CREATE TABLE IF NOT EXISTS 'doctorapp'.'".$email."' ('id' INT NOT NULL AUTO_INCREMENT, 'images' TEXT NOT NULL, PRIMARY KEY ('id'))";
//        echo $createTableQuery;
//        $createTable=mysqli_query($connectToDatabase, $createTableQuery);
//        if ($createTableQuery){
//            echo "created successfully";
//        }
//        else{
//            echo mysqli_error($createTableQuery);
//        }
        $query="INSERT INTO `urmi.mhrz@gmail.com` (`images`) VALUES ('ailelai yei')";
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

    mysqli_close($connectToDatabase);


?>