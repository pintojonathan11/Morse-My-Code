package com.morsemycode.jonathan.morsecodetranslator;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Conversion {
    String convert;
    HashMap<Character,String> hashMap;

    public Conversion(String Convert) {
        convert=Convert;
        hashMap = new HashMap<>();
        fillMap();

    }
    public String TextToMorseConversion(){//Includes SpeechToText
        convert=convert.toLowerCase();
        String ret="";
        boolean prev_space=false;
        for(int i=0;i<convert.length();i++){
            if(convert.charAt(i)==' '&&!prev_space){
                ret+="  ";//1 space from letter conversion+2 space= 3 spaces to show word difference vs letter difference
                prev_space=true;
            }
            else if(convert.charAt(i)!=' '){
                ret=ret+hashMap.get(convert.charAt(i))+" ";//1 space after each morse letter conversion
                prev_space=false;
            }
        }

        return ret;
    }
    public String MorseToTextConversion(){//1 space is new letter, 2 space is new word, 3 or more is also new word which will show has if it was 2 spaces
        String ret="";
        StringTokenizer st=new StringTokenizer(convert," ",true);
        boolean prev=false;
        while(st.hasMoreTokens()) {
            String s=st.nextToken();
            if(s.equals(" ")&&!ret.equals("")) {
                if(ret.charAt(ret.length()-1)!=' '&&!prev) {
                    prev=true;
                }
                else if(ret.charAt(ret.length()-1)!=' '&&prev){
                    ret+=" ";
                }
                else {
                }
            }
            else{
                for(Character c: hashMap.keySet()){
                    if(hashMap.get(c).equals(s)){
                        ret+=c;
                    }
                }
                prev=false;
            }
        }
        return ret;
    }
    public void fillMap(){
        hashMap.put('a',".-");
        hashMap.put('b',"-...");
        hashMap.put('c',"-.-.");
        hashMap.put('d',"-..");
        hashMap.put('e',".");
        hashMap.put('f',"..-.");
        hashMap.put('g',"--.");
        hashMap.put('h',"....");
        hashMap.put('i',"..");
        hashMap.put('j',".---");
        hashMap.put('k',"-.-");
        hashMap.put('l',".-..");
        hashMap.put('m',"--");
        hashMap.put('n',"-.");
        hashMap.put('o',"---");
        hashMap.put('p',".--.");
        hashMap.put('q',"--.-");
        hashMap.put('r',".-.");
        hashMap.put('s',"...");
        hashMap.put('t',"-");
        hashMap.put('u',"..-");
        hashMap.put('v',"...-");
        hashMap.put('w',".--");
        hashMap.put('x',"-..-");
        hashMap.put('y',"-.--");
        hashMap.put('z',"--..");
        hashMap.put('1',".----");
        hashMap.put('2',"..---");
        hashMap.put('3',"...--");
        hashMap.put('4',"....-");
        hashMap.put('5',".....");
        hashMap.put('6',"-....");
        hashMap.put('7',"--...");
        hashMap.put('8',"---..");
        hashMap.put('9',"----.");
        hashMap.put('0',"-----");
        hashMap.put('\n',"\n");
        hashMap.put('\'',"");
    }

}



