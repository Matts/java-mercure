package io.github.matts.mercure.http;


import io.github.matts.mercure.http.exceptions.ForbiddenException;
import io.github.matts.mercure.http.exceptions.NotFoundException;
import io.github.matts.mercure.http.exceptions.UnauthorizedException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client {

    private HttpClient httpClient;
    private String url;
    private String authorizationToken;

    public static final int DEFAULT_CONNECT_TIMEOUT_MS = 2_000;
    public static final int DEFAULT_SOCKET_TIMEOUT_MS = 5_000;
    private static final int CONNECTION_REQUEST_TIMEOUT_MS = 2_000;

    public Client(String url, String authorizationToken) {
        this(url, authorizationToken, DEFAULT_CONNECT_TIMEOUT_MS, DEFAULT_SOCKET_TIMEOUT_MS);
    }

    public Client(String url, String authorizationToken, int connectTimeoutMs, int socketTimeoutMs) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeoutMs)
                .setSocketTimeout(socketTimeoutMs)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MS)
                .build();
        this.httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        this.url = url;
        this.authorizationToken = authorizationToken;
    }

    public String sendRequest(Map<String, String> postData) throws UnauthorizedException, ForbiddenException,
            NotFoundException {
        String responseContent = "";
        HttpPost request = new HttpPost(this.url);
        request.addHeader("Authorization", "Bearer " + this.authorizationToken);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> parameters = new ArrayList<>();

        for (Map.Entry<String,String> entry : postData.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            request.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            HttpResponse response = this.httpClient.execute(request);
            if (response.getEntity() != null) {
                responseContent = EntityUtils.toString(response.getEntity());
            }
            switch (response.getStatusLine().getStatusCode()) {
                case HttpStatus.SC_UNAUTHORIZED:
                    throw new UnauthorizedException(responseContent);
                case HttpStatus.SC_FORBIDDEN:
                    throw new ForbiddenException(responseContent);
                case HttpStatus.SC_NOT_FOUND:
                    throw new NotFoundException();
            }
        } catch (IOException e) {
            throw new NotFoundException();
        }

        return responseContent;
    }
}
