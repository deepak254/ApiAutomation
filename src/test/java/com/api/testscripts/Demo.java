package com.api.testscripts;

import java.util.HashMap;
import java.util.Map;

public class Demo {


    public static void main(String[] args) {

        String inputStr="11111000000000";
        String outPutStr="";
        String tempStr="";
        for (int i=inputStr.length()-1;i>=0;i--){
            tempStr=inputStr.substring(i,i+1);
            outPutStr=outPutStr.concat(inputStr.substring(i,i+1));
        }
        System.out.println(outPutStr);


        Map<Character,Integer> map=new HashMap<>();

        char[] charArr=inputStr.toCharArray();
        for (char c:charArr){

            int count=0;
            Character objectTypeCharacter=new Character(c);
            if (map.containsKey(objectTypeCharacter)) {
                count = map.get(objectTypeCharacter) + 1;
                map.put(objectTypeCharacter,count);
            }
            else
                map.put(objectTypeCharacter,1);
        }

        for (Character c:map.keySet()){
            System.out.println("Character "+c +":  AND Count:"+map.get(c));
        }

    }
}
