package com.chan.common;

import com.chan.domain.Address;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

@Component
public class SigunguCode {

    private HashMap<Integer, String> localMapping = new HashMap<>();

    @PostConstruct
    public void init() throws IOException, ParseException {
        ClassPathResource resource = new ClassPathResource("json/localCodeMapping.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

        JSONArray centerList = (JSONArray) jsonObject.get("centerList");

        for(int i = 0; i < centerList.size(); i++){
            JSONObject center = (JSONObject) centerList.get(i);
            int sigunguCode = Integer.parseInt(center.get("sigunguCode").toString());
            String localCode = center.get("localCode").toString();
            localMapping.put(sigunguCode, localCode);
        }
        
    }

    public boolean isDelivery(Address address){
        return localMapping.containsKey(address.getSigunguCode());
    }

    public boolean isDelivery(String sigungu){
        return localMapping.containsKey(sigungu);
    }

    public String getLocalCode(Address address){
        return localMapping.get(address.getSigunguCode());
    }

    public String getLocalCode(String sigungu){
        return localMapping.get(sigungu);
    }

}
