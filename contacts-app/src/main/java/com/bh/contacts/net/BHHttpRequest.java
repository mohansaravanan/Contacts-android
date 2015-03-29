package com.bh.contacts.net;

import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public abstract class BHHttpRequest<T> {

    protected String uri;

    public BHHttpRequest(String uri) {
        this.uri = uri;
    }

    protected String getUri() {
        return uri;
    }

    protected T getRequest() throws IOException, JSONException {
        AndroidHttpClient client = AndroidHttpClient.newInstance(null);
        String responseJsonString = null;
        try {
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 45000);
            HttpConnectionParams.setSoTimeout(client.getParams(), 45000);

            HttpUriRequest httpGet = new HttpGet(getUri());
            HttpResponse httpResponse = client.execute(httpGet);
            responseJsonString = convertResponseIntoString(httpResponse);
            Log.d("Response Json ", responseJsonString);
        } catch (Exception e) {
            System.out.println("Exception during get contacts" + e);
        } finally {
            client.close();
        }

        return handleResponse(responseJsonString);
    }


    public T execute() {
        try {
            try {
                return getRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertResponseIntoString(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                InputStream inputStream = null;
                Header contentEncoding = response.getFirstHeader("Content-Encoding");
                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    inputStream = new GZIPInputStream(entity.getContent());
                } else {
                    inputStream = entity.getContent();
                }

                if (inputStream == null) {
                    return null;
                }

                String charset = null;
                if (entity.getContentType() != null) {
                    HeaderElement values[] = entity.getContentType().getElements();
                    if (values.length > 0) {
                        NameValuePair param = values[0].getParameterByName("charset");
                        if (param != null) {
                            charset = param.getValue();
                        }
                    }
                }

                if (charset == null) {
                    charset = HTTP.DEFAULT_CONTENT_CHARSET;
                }

                StringBuilder buffer = null;
                InputStreamReader ird = new InputStreamReader(inputStream, charset);
                BufferedReader brd = new BufferedReader(ird);
                buffer = new StringBuilder();

                String line;
                while ((line = brd.readLine()) != null) {
                    line = line.trim();
                    if (!TextUtils.isEmpty(line)) {
                        buffer.append(line);
                    }
                }
                ird.close();
                brd.close();
                return buffer.toString();
            } catch (IOException ioe) {
            }
        }
        return null;
    }

    protected abstract T handleResponse(String response) throws JSONException;

}
