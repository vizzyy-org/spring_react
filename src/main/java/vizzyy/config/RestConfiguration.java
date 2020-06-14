package vizzyy.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import vizzyy.service.S3ResourceService;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestConfiguration {

    private static final int MAX_TOTAL_CONNECTIONS = 10;
    private static final int MAX_ROUTE_CONNECTIONS = 10;
    @Value("${rest.keystore.path}")
    String keystorePath;

    @Value("${rest.truststore.path}")
    String truststorePath;

    @Value("${rest.keystore.secret}")
    String keystoreSecret;

    @Value("${rest.keystore.secret}")
    String truststoreSecret;

    FileInputStream keystoreInputStream = null;
    FileInputStream truststoreInputStream = null;

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(60*1000).build();
        poolingConnectionManager.setSocketConfig(new HttpHost("vizzyy.ddns.net"), socketConfig);
//        // set a total amount of connections across all HTTP routes
//        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
//        // set a maximum amount of connections for each HTTP route in pool
//        poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        return poolingConnectionManager;
    }

    @Bean
    public RestTemplate getRestTemplate() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, CertificateException {

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystoreInputStream = new FileInputStream(keystorePath);
        keystore.load(keystoreInputStream, keystoreSecret.toCharArray());
        keystoreInputStream.close();

        KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
        truststoreInputStream = new FileInputStream(truststorePath);
        truststore.load(truststoreInputStream, truststoreSecret.toCharArray());
        truststoreInputStream.close();

        SSLContext sslcontext = SSLContexts.custom().setProtocol("TLS")
                .loadKeyMaterial(keystore, keystoreSecret.toCharArray())
                .loadTrustMaterial(truststore, null).build(); //TS secret?

        //TODO: Sort out this hostname validation
        HostnameVerifier hostnameverifier = null;
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslcontext,
                null, null, hostnameverifier);
//        int timeout = 60; //seconds
//        RequestConfig config = RequestConfig.custom()
//                .setConnectTimeout(timeout * 1000)
//                .setConnectionRequestTimeout(timeout * 1000)
//                .setSocketTimeout(timeout * 1000).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
//                .setDefaultRequestConfig(config)
//                .setConnectionTimeToLive(1, TimeUnit.MINUTES)
                .build();
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setReadTimeout(timeout * 1000);
//        requestFactory.setConnectionRequestTimeout(timeout * 1000);
//        requestFactory.setConnectTimeout(timeout * 1000);
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

}
