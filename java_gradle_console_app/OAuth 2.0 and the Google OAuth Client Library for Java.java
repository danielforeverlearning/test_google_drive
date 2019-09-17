//*********************************************************************
//gradle stuff see Setup Instructions_google-oauth-java-client.pdf
//*********************************************************************

com.google.api.client.auth.oauth2 (from google-oauth-client)
com.google.api.client.extensions.servlet.auth.oauth2 (from google-oauth-client-servlet)
com.google.api.client.extensions.appengine.auth.oauth2 (from google-oauth-client-appengine)

(1) 
google-oauth-client
Google OAuth Client Library for Java (google-oauth-client) is designed to be compatible with all supported Java platforms, including Android.

Maven usage:
<dependency>
  <groupId>com.google.oauth-client</groupId>
  <artifactId>google-oauth-client</artifactId>
  <version>1.30.1</version>
</dependency>

(2)
google-oauth-client-servlet
Servlet and JDO extensions to the Google OAuth Client Library for Java (google-oauth-client-servlet) support Java servlet web applications. 
This module depends on google-oauth-client.

<dependency>
  <groupId>com.google.oauth-client</groupId>
  <artifactId>google-oauth-client-servlet</artifactId>
  <version>1.30.1</version>
</dependency>

(3)

google-oauth-client-appengine
Google App Engine extensions to the Google OAuth Client Library for Java (google-oauth-client-appengine) support Java Google App Engine applications. 
This module depends on google-oauth-client and google-oauth-client-servlet.

<dependency>
  <groupId>com.google.oauth-client</groupId>
  <artifactId>google-oauth-client-appengine</artifactId>
  <version>1.30.1</version>
</dependency>




//*****************************************
// Sample code: box1
//*****************************************

public class ServletSample extends AbstractAuthorizationCodeServlet {

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
    return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
        new NetHttpTransport(),
        new JacksonFactory(),
        new GenericUrl("https://server.example.com/token"),
        new BasicAuthentication("s6BhdRkqt3", "7Fjfp0ZBr1KtDRbnfVdmIw"),
        "s6BhdRkqt3",
        "https://server.example.com/authorize").setCredentialDataStore(
            StoredCredential.getDefaultDataStore(
                new FileDataStoreFactory(new File("datastoredir"))))
        .build();
  }

  @Override
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    // return user ID
  }
}

public class ServletCallbackSample extends AbstractAuthorizationCodeCallbackServlet {

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
    return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
        new NetHttpTransport(),
        new JacksonFactory(),
        new GenericUrl("https://server.example.com/token"),
        new BasicAuthentication("s6BhdRkqt3", "7Fjfp0ZBr1KtDRbnfVdmIw"),
        "s6BhdRkqt3",
        "https://server.example.com/authorize").setCredentialDataStore(
            StoredCredential.getDefaultDataStore(
                new FileDataStoreFactory(new File("datastoredir"))))
        .build();
  }

  @Override
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    // return user ID
  }
}


//*************************************************
// Sample code box2
//*************************************************

public class AppEngineSample extends AbstractAppEngineAuthorizationCodeServlet {

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
    return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
        new UrlFetchTransport(),
        new JacksonFactory(),
        new GenericUrl("https://server.example.com/token"),
        new BasicAuthentication("s6BhdRkqt3", "7Fjfp0ZBr1KtDRbnfVdmIw"),
        "s6BhdRkqt3",
        "https://server.example.com/authorize").setCredentialStore(
            StoredCredential.getDefaultDataStore(AppEngineDataStoreFactory.getDefaultInstance()))
        .build();
  }
}

public class AppEngineCallbackSample extends AbstractAppEngineAuthorizationCodeCallbackServlet {

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
    return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
        new UrlFetchTransport(),
        new JacksonFactory(),
        new GenericUrl("https://server.example.com/token"),
        new BasicAuthentication("s6BhdRkqt3", "7Fjfp0ZBr1KtDRbnfVdmIw"),
        "s6BhdRkqt3",
        "https://server.example.com/authorize").setCredentialStore(
            StoredCredential.getDefaultDataStore(AppEngineDataStoreFactory.getDefaultInstance()))
        .build();
  }
}


//********************************************************
//Sample code box3
//********************************************************

/** Authorizes the installed application to access user's protected data. */
private static Credential authorize() throws Exception {
  OAuth2ClientCredentials.errorIfNotSpecified();
  // set up authorization code flow
  AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(BearerToken
      .authorizationHeaderAccessMethod(),
      HTTP_TRANSPORT,
      JSON_FACTORY,
      new GenericUrl(TOKEN_SERVER_URL),
      new ClientParametersAuthentication(
          OAuth2ClientCredentials.API_KEY, OAuth2ClientCredentials.API_SECRET),
      OAuth2ClientCredentials.API_KEY,
      AUTHORIZATION_SERVER_URL).setScopes(Arrays.asList(SCOPE))
      .setDataStoreFactory(DATA_STORE_FACTORY).build();
  // authorize
  LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(
      OAuth2ClientCredentials.DOMAIN).setPort(OAuth2ClientCredentials.PORT).build();
  return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
}

private static void run(HttpRequestFactory requestFactory) throws IOException {
  DailyMotionUrl url = new DailyMotionUrl("https://api.dailymotion.com/videos/favorites");
  url.setFields("id,tags,title,url");

  HttpRequest request = requestFactory.buildGetRequest(url);
  VideoFeed videoFeed = request.execute().parseAs(VideoFeed.class);
  ...
}

public static void main(String[] args) {
  ...
  DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
  final Credential credential = authorize();
  HttpRequestFactory requestFactory =
      HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
        @Override
        public void initialize(HttpRequest request) throws IOException {
          credential.initialize(request);
          request.setParser(new JsonObjectParser(JSON_FACTORY));
        }
      });
  run(requestFactory);
  ...
}


//******************************************************
//Sample code box4
//******************************************************

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  String url = new BrowserClientRequestUrl(
      "https://server.example.com/authorize", "s6BhdRkqt3").setState("xyz")
      .setRedirectUri("https://client.example.com/cb").build();
  response.sendRedirect(url);
}


//********************************************************
//box5
//*********************************************************

   HTTP/1.1 401 Unauthorized
   WWW-Authenticate: Bearer realm="example",
                     error="invalid_token",
                     error_description="The access token expired"