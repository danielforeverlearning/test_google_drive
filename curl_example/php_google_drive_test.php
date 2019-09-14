<?php

	//$curl = curl_init( 'https://public-api.wordpress.com/oauth2/token' );
	curl_setopt( $curl, CURLOPT_POST, true );
	curl_setopt( $curl, CURLOPT_POSTFIELDS, array(
    'apiKey' => your_api_key,
	'client_id' => your_client_id,
    'discoveryDocs' => ["https://www.googleapis.com/discovery/v1/apis/drive/v3/rest"],
    'scope' => 'https://www.googleapis.com/auth/drive.metadata.readonly') );
	curl_setopt( $curl, CURLOPT_RETURNTRANSFER, 1);
	$auth = curl_exec( $curl );
	
	printf("******************************</br>");
	printf("var_dump auth</br>");
	printf("******************************</br>");
	var_dump($auth);
	
	
	$secret = json_decode($auth);
	$access_key = $secret->access_token;
	
	
	//$curl = curl_init( 'https://public-api.wordpress.com/oauth2/token' );
	curl_setopt( $curl, CURLOPT_HTTPHEADER, array( 'Authorization: Bearer ' . $access_key ) );
	curl_setopt( $curl, CURLOPT_POST, true );
	curl_setopt( $curl, CURLOPT_POSTFIELDS, array(
    'pageSize' => 10,
	'fields' => "nextPageToken, files(id, name)") );
	curl_setopt( $curl, CURLOPT_RETURNTRANSFER, 1);
	$filelistresponse = curl_exec( $curl );
	
	printf("******************************</br>");
	printf("var_dump filelistresponse</br>");
	printf("******************************</br>");
	var_dump($filelistresponse);

?>