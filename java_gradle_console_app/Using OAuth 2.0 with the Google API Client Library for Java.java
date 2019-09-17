//****************************************************************************************
//gradle stuff see top of the .pdf file "Setup Instructions_google-api-java-client.pdf"
//****************************************************************************************
com.google.api.client.googleapis.auth.oauth2 (from google-api-client)
com.google.api.client.googleapis.extensions.appengine.auth.oauth2 (from google-api-client-appengine)

(1) 
google-api-client
The Google API Client Library for Java (google-api-client) is designed to be compatible with all supported Java platforms, including Android.

Maven usage:
<dependency>
  <groupId>com.google.api-client</groupId>
  <artifactId>google-api-client</artifactId>
  <version>1.30.2</version>
</dependency>

(2)
google-api-client-appengine
Google App Engine extensions to the Google API Client Library for Java (google-api-client-appengine) support Java Google App Engine applications. This module depends on google-api-client, google-api-client-servlet, google-oauth-client-appengine and google-http-client-appengine.

Maven usage:
<dependency>
  <groupId>com.google.api-client</groupId>
  <artifactId>google-api-client-appengine</artifactId>
  <version>1.30.2</version>
</dependency>



//******************************************
//box1
//******************************************
GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
Plus plus = new Plus.builder(new NetHttpTransport(),
                             JacksonFactory.getDefaultInstance(),
                             credential)
    .setApplicationName("Google-PlusSample/1.0")
    .build();

//********************************************
//box2
//********************************************
static Urlshortener newUrlshortener() {
  AppIdentityCredential credential =
      new AppIdentityCredential(
          Collections.singletonList(UrlshortenerScopes.URLSHORTENER));
  return new Urlshortener.Builder(new UrlFetchTransport(),
                                  JacksonFactory.getDefaultInstance(),
                                  credential)
      .build();
}

//****************************************************
//box 3
//****************************************************

public class CalendarServletSample extends AbstractAuthorizationCodeServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // do stuff
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath("/oauth2callback");
    return url.build();
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
        "[[ENTER YOUR CLIENT ID]]", "[[ENTER YOUR CLIENT SECRET]]",
        Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
        DATA_STORE_FACTORY).setAccessType("offline").build();
  }

  @Override
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    // return user ID
  }
}

public class CalendarServletCallbackSample extends AbstractAuthorizationCodeCallbackServlet {

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException {
    resp.sendRedirect("/");
  }

  @Override
  protected void onError(
      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
      throws ServletException, IOException {
    // handle error
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath("/oauth2callback");
    return url.build();
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return new GoogleAuthorizationCodeFlow.Builder(
        new NetHttpTransport(), JacksonFactory.getDefaultInstance()
        "[[ENTER YOUR CLIENT ID]]", "[[ENTER YOUR CLIENT SECRET]]",
        Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
        DATA_STORE_FACTORY).setAccessType("offline").build();
  }

  @Override
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    // return user ID
  }
}


//**************************************************
//box 4
//**************************************************

public class CalendarAppEngineSample extends AbstractAppEngineAuthorizationCodeServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // do stuff
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return Utils.getRedirectUri(req);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return Utils.newFlow();
  }
}

class Utils {
  static String getRedirectUri(HttpServletRequest req) {
    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath("/oauth2callback");
    return url.build();
  }

  static GoogleAuthorizationCodeFlow newFlow() throws IOException {
    return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        getClientCredential(), Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(
        DATA_STORE_FACTORY).setAccessType("offline").build();
  }
}

public class OAuth2Callback extends AbstractAppEngineAuthorizationCodeCallbackServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException {
    resp.sendRedirect("/");
  }

  @Override
  protected void onError(
      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
      throws ServletException, IOException {
    String nickname = UserServiceFactory.getUserService().getCurrentUser().getNickname();
    resp.getWriter().print("<h3>" + nickname + ", why don't you want to play with me?</h1>");
    resp.setStatus(200);
    resp.addHeader("Content-Type", "text/html");
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return Utils.getRedirectUri(req);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return Utils.newFlow();
  }
}

//************************************************
//box 5
//************************************************

HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
...
// Build service account credential.

GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("MyProject-1234.json"))
    .createScoped(Collections.singleton(PlusScopes.PLUS_ME));
// Set up global Plus instance.
plus = new Plus.Builder(httpTransport, jsonFactory, credential)
    .setApplicationName(APPLICATION_NAME).build();
...

//**************************************************
//box 6
//**************************************************

public static void main(String[] args) {
  try {
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
    // authorization
    Credential credential = authorize();
    // set up global Plus instance
    plus = new Plus.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
        APPLICATION_NAME).build();
   // ...
}

private static Credential authorize() throws Exception {
  // load client secrets
  GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
      new InputStreamReader(PlusSample.class.getResourceAsStream("/client_secrets.json")));
  // set up authorization code flow
  GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
      httpTransport, JSON_FACTORY, clientSecrets,
      Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(
      dataStoreFactory).build();
  // authorize
  return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
}

//*************************************************
//box 7
//*************************************************
	
public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException {
  String url = new GoogleBrowserClientRequestUrl("812741506391.apps.googleusercontent.com",
      "https://oauth2.example.com/oauthcallback", Arrays.asList(
          "https://www.googleapis.com/auth/userinfo.email",
          "https://www.googleapis.com/auth/userinfo.profile")).setState("/profile").build();
  response.sendRedirect(url);
}

//***************************************************
//box 8
//***************************************************
	
com.google.api.services.tasks.Tasks service;

@Override
public void onCreate(Bundle savedInstanceState) {
  credential =
      GoogleAccountCredential.usingOAuth2(this, Collections.singleton(TasksScopes.TASKS));
  SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
  credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
  service =
      new com.google.api.services.tasks.Tasks.Builder(httpTransport, jsonFactory, credential)
          .setApplicationName("Google-TasksAndroidSample/1.0").build();
}

private void chooseAccount() {
  startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  switch (requestCode) {
    case REQUEST_GOOGLE_PLAY_SERVICES:
      if (resultCode == Activity.RESULT_OK) {
        haveGooglePlayServices();
      } else {
        checkGooglePlayServicesAvailable();
      }
      break;
    case REQUEST_AUTHORIZATION:
      if (resultCode == Activity.RESULT_OK) {
        AsyncLoadTasks.run(this);
      } else {
        chooseAccount();
      }
      break;
    case REQUEST_ACCOUNT_PICKER:
      if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
        String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
        if (accountName != null) {
          credential.setSelectedAccountName(accountName);
          SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = settings.edit();
          editor.putString(PREF_ACCOUNT_NAME, accountName);
          editor.commit();
          AsyncLoadTasks.run(this);
        }
      }
      break;
  }
}










