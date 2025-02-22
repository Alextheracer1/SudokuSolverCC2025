package org.skillissue;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLBypass {

  public static void disableSSLVerification() throws Exception {

    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

    TrustManager[] trustAllCertificates = new TrustManager[]{
        new X509TrustManager() {
          public void checkClientTrusted(X509Certificate[] chain, String authType) {}
          public void checkServerTrusted(X509Certificate[] chain, String authType) {}
          public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }
    };
    SSLContext sc = SSLContext.getInstance("TLS");
    sc.init(null, trustAllCertificates, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
  }
}
