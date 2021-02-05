package com.extension.clova.businform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExtensionMessage {
    public String version = "1.0";
    public Map<String, Object> sessionAttributes = new HashMap<>();
    public MyResponse response = null;
    public ExtensionMessage(String INTENT_NAME, boolean session, ArrayList<Map> myResponseValue) {

        if(!session) {
            sessionAttributes.put("intent", INTENT_NAME);
        }

        MyResponse response = new MyResponse();
        response.shouldEndSession = session;
        response.outputSpeech.put("type", "SpeechList");
        response.outputSpeech.put("values", myResponseValue);
        this.response = response;
    }
    public ExtensionMessage(String INTENT_NAME, String message, boolean session, String type) {
        Map<String, String> myResponseValue = new HashMap<String, String>();
        myResponseValue.put("type", type);
        myResponseValue.put("lang", "ko");
        myResponseValue.put("value", message);

        if(!session) {
            sessionAttributes.put("intent", INTENT_NAME);
        }

        MyResponse response = new MyResponse();
        response.shouldEndSession = session;
        response.outputSpeech.put("type", "SimpleSpeech");
        response.outputSpeech.put("values", myResponseValue);
        this.response = response;
    }
    public static class MyResponse {

        public Map<String, Object> outputSpeech = new HashMap<String, Object>();
        public Map<String, Map> card = new HashMap<String, Map>();
        public ArrayList<String> directives = new ArrayList<String>();
        public boolean shouldEndSession = false;
    }
}