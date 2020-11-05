package lab2;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Service {
    public String getData(String line){
        StringBuffer data=new StringBuffer("");
        try {
            if(line.toLowerCase().equals("hello")){
                String json=JsonToString.jsonGetRequest("https://api.airvisual.com/v2/countries?key=5a610536-fc63-4a27-8f28-738631b0e606");
                List<String> countries= new ArrayList<>();
                if (json!=null&&getList("country",json)!=null){
                    countries=getList("country",json);
                    for (String s: countries){
                        data.append(s+"\n");
                    }
                    String test=data.toString();
                    return data.toString();
                }
                else {
                    return "error country";
                }
            }
            else {
                if (new StringTokenizer(line,";").countTokens()>1)
                {
                    if(new StringTokenizer(line,";").countTokens()>2){
                        StringTokenizer tokenizer= new StringTokenizer(line,";");
                        String country=tokenizer.nextToken();
                        String state=tokenizer.nextToken();
                        String city=tokenizer.nextToken();
                        String json=JsonToString.jsonGetRequest("https://api.airvisual.com/v2/city?city="+city+"&state="+state+"&country="+country+"&key=5a610536-fc63-4a27-8f28-738631b0e606");
                        List<String> pollutions= new ArrayList<>();
                        List<String> strings=getList("pollution",json);
                        if (json!=null&&getList("pollution",json)!=null){
                            pollutions=getList("pollution",json);
                            for (String s: pollutions){
                                data.append(s+"\n");
                            }
                            return data.toString();
                        }
                        else {
                            return "error pollution";
                        }

                    }
                    else {
                        StringTokenizer tokenizer= new StringTokenizer(line,";");
                        String country=tokenizer.nextToken();
                        String state=tokenizer.nextToken();
                        String json=JsonToString.jsonGetRequest("https://api.airvisual.com/v2/cities?state="+state+"&country="+country+"&key=5a610536-fc63-4a27-8f28-738631b0e606");
                        List<String> cities= new ArrayList<>();
                        if (json!=null&&getList("city",json)!=null){
                            cities=getList("city",json);
                            for (String s: cities){
                                data.append(s+"\n");
                            }
                            return data.toString();
                        }
                        else {
                            return "error city";
                        }
                    }
                }
                else {
                    String json=JsonToString.jsonGetRequest("https://api.airvisual.com/v2/states?country="+line+"&key=5a610536-fc63-4a27-8f28-738631b0e606");
                    List<String> states= new ArrayList<>();
                    if (json!=null&&getList("state",json)!=null){
                        states=getList("state",json);
                        for (String s: states){
                            data.append(s+"\n");
                        }
                        return data.toString();
                    }
                    else {
                        return "error state";
                    }
                }
            }
        }
        catch (Exception e){
            return "network error";
        }



    }
    public List<String> getList(String key,String json){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj= (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String status=(String)obj.get("status");


        if (status.equals("success")){
            List<String> list = new ArrayList<String>();


            if (key.equals("pollution")){
                JSONObject jsonObject = (JSONObject) obj.get("data");
                JSONObject csonObject = (JSONObject) jsonObject.get("current");
                JSONObject pjsonObject = (JSONObject) csonObject.get("pollution");

                list.add("aqi="+pjsonObject.get("aqius").toString());
                return list;
            }
            else {
                JSONArray array = (JSONArray) obj.get("data");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = (JSONObject) array.get(i);
                    list.add((String) jsonObject.get(key));
                }
                return list;
            }
        }
        return  null;
    }
}
