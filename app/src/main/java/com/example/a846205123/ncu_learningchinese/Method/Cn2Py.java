package com.example.a846205123.ncu_learningchinese.Method;

import java.util.Arrays;
import java.util.List;

public class Cn2Py {
    public static final int HANZI_START = 19968;//漢字開始位置
    public static final int HANZI_END = 40869;//结束位置
    public static final int HANZI_COUNT = 20902;//漢字總個數
    private static List<String> pyDicList;//字典集合


    private static void readPyDicList() {
        StringBuffer pyDicStr = new StringBuffer();
        pyDicStr.append(PinYinDic.INDEX_24968)
                .append(PinYinDic.INDEX_29969)
                .append(PinYinDic.INDEX_34970)
                .append(PinYinDic.INDEX_39971)
                .append(PinYinDic.INDEX_40869);
        String[] pyStr = pyDicStr.toString().split(",");
        pyDicList = Arrays.asList(pyStr);
    }

    public static String getFullPinYin(String name) {
        if (pyDicList == null) {
            readPyDicList();
        }
        name = name.replace(" ", "");
        StringBuffer pinYinGroup = new StringBuffer();
        for (int i = 0; i < name.length(); i++) {
            String n = name.substring(i, i + 1);
            int uniCode = n.charAt(0);
            if (uniCode >= HANZI_START && uniCode <= HANZI_END) {
                String pinyin = String.valueOf(pyDicList.get(uniCode - HANZI_START));
                pinYinGroup.append(pinyin + " ");
            } else {
                pinYinGroup.append(n + " ");
            }
        }
        return pinYinGroup.toString()
                .substring(0, pinYinGroup.length() - 1);
    }
}
