package com.extension.clova.businform.controller;

import com.extension.clova.businform.ExtensionMessage;
import com.extension.clova.businform.XmlParsing_test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clova")
public class ExtensionController {

    private NodeList nListR;
    private XmlParsing_test xmlparser;

    @ResponseBody
    @RequestMapping(value = "v1/hello", method = RequestMethod.GET, produces = "application/json")
    public String hello() {
        return "hello clova api";
    }

    @RequestMapping(value = "/extention", method = RequestMethod.POST, produces = "applicaion/json")
    @ResponseBody
    public ResponseEntity<ExtensionMessage> weather (Map<String, Object> map) {
        // 파싱해서 LaunchRequest 인지 IntentRequest 인지 SessionEndedRequest 인지 확인
        Map m = (HashMap)map.get("request");
        String type = (String)m.get("type");
        ExtensionMessage message = null;

        if(type.equals("LaunchRequest")) { // extension 시작
            message = new ExtensionMessage("SampleIntent", "물어보세요.", false, "PlainText");
        }
        else if (type.equals("IntentRequest")) { // extension의 인텐트 시작
            Map intent = (HashMap) m.get("intent");
            String intentName = (String) intent.get("callbusIntent");
            Map slots = (HashMap) intent.get("slots"); // slot
            String slotName = "";
            String slotValue = "";

            if (intentName.equals("SampleIntent")) {
                if (slots != null) {
                    Map myslot = (HashMap) slots.get("busSlotType");
                    slotName = (String) myslot.get("name");
                    slotValue = (String) myslot.get("value");
                    System.out.println("slotName===" + slotName); // 버스 어디있어
                    System.out.println("slotValue===" + slotValue); //15
                }

                /*

                api
                명령처리
                서버주소 얻기
                 */
                // 명령 처리
                for(int temp = 0; temp < nListR.getLength(); temp++){
                    Node nNode = nListR.item(temp);
                    if (slotValue.equals(getTagValue("routeName"))) {
                        message= new ExtensionMessage("SampleIntent", slotValue + "번 버스는 어디있습니다.", true, "PlainText");
                    }
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){
                        Element eElement = (Element) nNode;
                    }	// for end
                // Built-in Intent 처리
            }
            else if (intentName.equals("Clova.YesIntent")) { // 명령 확인
                message = new ExtensionMessage(intentName, "예 라고 하셨나요?", true, "PlainText");
            }
            else if (intentName.equals("Clova.NoIntent")) { // 명령 확인
                message = new ExtensionMessage(intentName, "노 라고 하셨나요?", true, "PlainText");
            }
            else if (intentName.equals("Clova.GuideIntent")) { // 가이드 알려주기
                message = new ExtensionMessage("hearTestIntent", "15번 버스 어디있어 라고 말해보세요.", false, "PlainText");
            } else if (intentName.equals("Clova.CancelIntent")) { // 잘못된 응답 취소
                message = new ExtensionMessage("hearTestIntent", "작업을 취소합니다.", true, "PlainText");
            }
        } else if (type.equals("SessionEndedRequest")) { // extension 종료
            message = new ExtensionMessage("turnOnIntent", "extention을 종료합니다.", false, "PlainText");
        }
        return new ResponseEntity<ExtensionMessage>(message, HttpStatus.OK); }
}
