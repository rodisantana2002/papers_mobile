package com.rhcloud.papers.helpers.rest;

import android.content.Context;
import com.rhcloud.papers.excecoes.excPassaErro;
import com.rhcloud.papers.helpers.generic.hlpConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Rodolfo on 24/07/2017
 */

public class hlpConnectionURL {
    private HttpURLConnection conn;
    private URL url;
    private Context ctx;

    public hlpConnectionURL() {
        conn = null;
        URL url = null;
    }

    public String get(String path) throws excPassaErro {
        return simpleResponse(hlpConstants.TYPE_REST_GET, path);
    }

    public String delete(String path) throws excPassaErro {
        return simpleResponse(hlpConstants.TYPE_REST_DELETE, path);
    }

    public String put(String path, String json) throws excPassaErro {
        return complexResponse(hlpConstants.TYPE_REST_PUT, path, json);
    }

    public String post(String path, String json) throws excPassaErro {
        return complexResponse(hlpConstants.TYPE_REST_POST, path, json);
    }

    public void addHearders(String key, String value) {
        conn.setRequestProperty(key, value);
    }

    private String simpleResponse(String type, String path) throws excPassaErro {
        String response = "";
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(type);

            if (conn.getResponseCode() == 200) {
                response = readStream(conn.getInputStream());

            } else if (conn.getResponseCode() == 204) {
                response = "";
            } else if (conn.getResponseCode() == 400) {
                throw new excPassaErro(hlpConstants.MSG_400);
            } else if (conn.getResponseCode() == 404) {
                throw new excPassaErro(hlpConstants.MSG_404);
            } else if (conn.getResponseCode() == 401) {
                throw new excPassaErro(hlpConstants.MSG_401);
            } else if (conn.getResponseCode() == 500) {
                throw new excPassaErro(hlpConstants.MSG_500);
            } else if (conn.getResponseCode() == 503) {
                throw new excPassaErro(hlpConstants.MSG_503);
            }
        } catch (IOException e) {
            throw new excPassaErro(hlpConstants.MSG_IOE);
        } finally {
            conn.disconnect();
        }

        return response;
    }

    private String complexResponse(String type, String path, String json) throws excPassaErro {
        String response = "";

        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod(type);
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json);
            wr.flush();
            response = readStream(conn.getInputStream());

            if (conn.getResponseCode() == 200) {
            } else if (conn.getResponseCode() == 204) {
                response = "";
            } else if (conn.getResponseCode() == 400) {
                throw new excPassaErro(hlpConstants.MSG_400);
            } else if (conn.getResponseCode() == 404) {
                throw new excPassaErro(hlpConstants.MSG_404);
            } else if (conn.getResponseCode() == 401) {
                throw new excPassaErro(hlpConstants.MSG_401);
            } else if (conn.getResponseCode() == 500) {
                throw new excPassaErro(hlpConstants.MSG_500);
            } else if (conn.getResponseCode() == 503) {
                throw new excPassaErro(hlpConstants.MSG_503);
            }
        } catch (IOException e) {
            throw new excPassaErro(hlpConstants.MSG_IOE);
        } finally {
            conn.disconnect();
        }

        return response;
    }

    private String readStream(InputStream in) throws excPassaErro {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch (IOException e) {
            throw new excPassaErro(hlpConstants.MSG_ERRO_JSON);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new excPassaErro(hlpConstants.MSG_ERRO_JSON);
                }
            }
        }
        return builder.toString();
    }
}


