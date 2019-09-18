https://www.boraji.com/how-to-create-a-gradle-java-project-in-eclipse
https://www.boraji.com/how-to-create-a-gradle-java-project-in-eclipse

https://developers.google.com/drive/api/v3/quickstart/java
https://developers.google.com/drive/api/v3/quickstart/java

https://developers.google.com/api-client-library/java/google-api-java-client/oauth2
https://developers.google.com/api-client-library/java/google-api-java-client/oauth2

https://gradle.org/install/
https://gradle.org/install/

http://console.developers.google.com
http://console.developers.google.com

(1) see Troubleshoot1.jpg
(2) google search on string "Resolve dependencies of :compileClasspath"
(3) try to fix step ..... add jcenter() to repositories inside build.gradle file
(4) see Troubleshoot2.jpg
(5) try to fix step ..... added new file called gradle.properties with path to jdk, put file in same folder as build.gradle

org.gradle.java.home=C:\\Program Files\\Java\\jdk1.8.0_144

(6) did "gradle run" from command prompt and 
ohmygoodness it did open up browser 
and i entered google sign-in credentials for my google account 
and had to click around giving permissions in the pages that kept popping up, 
sent to my phone another authentication step, 
went to my phone and pressed right number,  
then see Troubleshoot3.jpg

(7) ok .... still kept trying ..... 
still bombs on line 57 of DriveQuickstart.java see Troubleshoot_java_sourcecode_1.jpg

(8) ok uploaded documentation from 2 websites as .pdf and .java
  (A) Using OAuth 2.0 with the Google API Client Library for Java
  (B) OAuth 2.0 and the Google OAuth Client Library for Java

(9) For  (8A)  found source code in github:
https://github.com/google/google-api-java-client-samples/tree/master/plus-cmdline-sample
lets try to use this code before trying (8B),
uploading "google-api-java-client-samples-master.zip" from github
look at app called "plus-cmdline-sample"

(10) forget about step9 above,
got it to work at home, not at work, must be network admins blocking something or not-whitelisting something or blocking ports or whatever.

(11) at home this is how i got it working, its all in test.zip:
(a) went back to https://developers.google.com/drive/api/v3/quickstart/java
used exact same code except for this change:

//LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
LocalServerReceiver receiver = new LocalServerReceiver();

(b) used jdk by changing gradle.properties to
org.gradle.java.home=C:\\Program Files\\Android\\jdk\\microsoft_dist_openjdk_1.8.0.25
(c) installed gradle put to C:\Gradle\gradle-5.6.2
() set environment variables for JAVA_HOME and Path
(d) console.developers.google.com --> authenticate/sign in manually --> make sure project is correct --> Credentials --> make sure there is a "OAuth 2.0 Client IDs" of type Other otherwise click "Create credentials"-->"OAuth client ID"-->Other-->Create
(e) download the json
(f) rename it to credentials.json and put in Desktop\test\src\main\resources (assuming you unzipped test.zip to desktop).
(g) C:\Gradle\gradle-5.6.2\bin gradle clean
C:\Gradle\gradle-5.6.2\bin gradle run
and saw list of files!!!!!










