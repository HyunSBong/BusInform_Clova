package com.extension.clova.businform;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import java.net.URL;

public class XmlParsing_test {

    // tag값의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public static void main(String[] args) {
        try{
            while(true){
                // parsing할 url 지정(API 키 포함해서)
                String servicePath = "http://openapi.gbis.go.kr/ws/rest/";
                String serviceName = "busstationservice";
                String operation = "/route?";
                String serviceKey = "serviceKey=2LGrVBKRbUxVD5dXYkOPLb9Sar7XnzXiJ4REz2%2FS60MTHKOjsVBL7ZL6wKMrBomsdEVmDHmH9xW7J2hvtgllxA%3D%3D";
                String param1 = "&stationId";
                String param2 = "209000095";
                String url = servicePath + serviceName + operation + serviceKey + param1 + param2;

                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("busRouteList");
                //System.out.println("파싱할 리스트 수 : "+ nList.getLength());

                for(int temp = 0; temp < nList.getLength(); temp++){
                    Node nNode = nList.item(temp);
                    if(nNode.getNodeType() == Node.ELEMENT_NODE){

                        Element eElement = (Element) nNode;
                        System.out.println("######################");
                        //System.out.println(eElement.getTextContent());
                        System.out.println("버스 아이디  : " + getTagValue("routeName", eElement));
                        System.out.println("정류장 아이디  : " + getTagValue("routeTypeCd", eElement));
                        System.out.println("??? : " + getTagValue("routeTypeName", eElement));
                    }	// for end
                }	// if end
            }	// while end

        } catch (Exception e){
            e.printStackTrace();
        }	// try~catch end
    }	// main end
}	// class end
