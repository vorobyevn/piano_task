package com.api.stackexchange;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public final class RequestHelper {
    public static String GetGzip(URL url) throws Exception {
        System.out.println(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestMethod("GET");
        BufferedReader in = null;
        StringBuffer resp = null;
        InputStreamReader reader = null;
        GZIPInputStream gzipInputStream = null;
        
		int responseCode = connection.getResponseCode();
		if (responseCode != 200)
		{
			throw new Exception(String.format("Error, status code %d", responseCode));
		}
		
        try {
            InputStream inputStream = connection.getInputStream();
			gzipInputStream = new GZIPInputStream(inputStream);
            reader = new InputStreamReader(gzipInputStream, "UTF-8");
            in = new BufferedReader(reader);
            String inputLine;
            resp = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                resp.append(inputLine);
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        finally {
            if (gzipInputStream != null) {
                gzipInputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (in != null) {
                in.close();
            }
        }

        return resp.toString();
    }
}