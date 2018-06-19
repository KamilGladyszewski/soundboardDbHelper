
<?php
//error_reporting(0);
$data = array();
$errors = array();

if (empty($_POST['link']))
$errors['link'] = 'Wpisz link';

if (empty($_POST['txt']))
$errors['txt'] = 'Wpisz dźwięk';

if (empty($_POST['time']))
$errors['time'] = 'Wpisz czas';


if(!empty($errors)) {

    $data['success'] = false;
    $data['errors'] = $errors;


} else {
    $data['success'] = true;

    $link = mysqli_connect("localhost","<username>","<password>","<database name>");

    if (!$link) {
        $errors['mysql'] = mysqli_connect_error();
        $data['success'] = false;
        $data['errors'] = $errors;
    }else{  
        $sql = "INSERT INTO sound(url, text, time, vid_id) VALUES ('" .$_POST["link"]."','".$_POST["txt"]."','".$_POST["time"]."','".$_POST["id"]."')";

        if(mysqli_query($link, $sql)){
            $data['message'] = "db insert successful";
        }else{
            $data['message'] = mysqli_error($link);
        }
        
        $link -> close();
    }
}

echo json_encode($data);
?>