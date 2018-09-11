
import com.full.cn.utils.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by full on 2017/6/12.   http://wthrcdn.etouch.cn/WeatherApi?citykey=101020700
 */
public class TestWeatherApi {
    //private static final String targetURL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101020300";
    private static final String targetURL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101020400";

    public static String getData(){
        StringBuffer weatherdataXml = new StringBuffer();
        try {
            URL restServiceURL = new URL(targetURL);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/xml");

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }
            InputStream inputStream = new GZIPInputStream(httpConnection.getInputStream());
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    inputStream,"UTF-8"));
            String output;
            System.out.println("Output from Server:  \n");
            while ((output = responseBuffer.readLine()) != null) {
              //  System.out.println(output);
                weatherdataXml.append(output);
            }
            httpConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherdataXml.toString();
    }
    @Test
    public void xmlTojsonString(){
        try {
            String jsonObject = JsonUtil.xml2jsonString(TestWeatherApi.getData());
            //System.out.println(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void xmlTojsonObject(){
        Map<String,Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = JsonUtil.xml2jsonObject(TestWeatherApi.getData());
            JSONObject resp = (JSONObject) jsonObject.get("resp");
            JSONObject jsonObject2 = (JSONObject) resp.get("forecast");
            dataMap.put("city",resp.get("city"));
            dataMap.put("wendu",resp.get("wendu"));
            dataMap.put("shidu",resp.get("shidu"));
            dataMap.put("updatatime",resp.get("updatetime"));
            dataMap.put("fengxiang",resp.get("fengxiang"));

            JSONArray jsonArray = (JSONArray) jsonObject2.get("weather");
            JSONObject jsonObject3 =  jsonArray.getJSONObject(0);

            JSONObject weather = null;
            JSONObject night = null;
            JSONObject day = null;
            List<Object> listmap = new ArrayList<Object>();
            Map<String,Object> overmap = new HashMap<String, Object>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Map<String,Object> dataMap_ = new HashMap<String, Object>();
                Map<String,Object> nightmap = new HashMap<String, Object>();
                Map<String,Object> daymap = new HashMap<String, Object>();
                weather =  jsonArray.getJSONObject(i);
                night = (JSONObject) weather.get("night");
                nightmap.put("fengxiang",night.get("fengxiang"));
                nightmap.put("fengli",night.get("fengli"));
                nightmap.put("type",night.get("type"));
                day = (JSONObject) weather.get("day");
                daymap.put("fengxiang",day.get("fengxiang"));
                daymap.put("fengli",day.get("fengli"));
                daymap.put("type",day.get("type"));

                dataMap_.put("high",weather.get("high"));
                dataMap_.put("low",weather.get("low"));
                dataMap_.put("date",weather.get("date"));
                dataMap_.put("night",nightmap);
                dataMap_.put("day",daymap);
                listmap.add(dataMap_);


            }
            overmap.put("maplist",listmap);
            overmap.put("dataMap",dataMap);
            //listmap.add(dataMap);
            System.out.println(overmap.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


