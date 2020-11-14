package com.example.myapplication;
import java.util.Comparator;
import net.sourceforge.pinyin4j.PinyinHelper;
public class MyChineseComparator implements Comparator<Object>{
    public int compare(Object o1, Object o2) {
        char c1 = ((String) o1).charAt(0);
        char c2 = ((String) o2).charAt(0);
        return concatPinyinStringArray(
                PinyinHelper.toHanyuPinyinStringArray(c1)).compareTo(
                concatPinyinStringArray(PinyinHelper
                        .toHanyuPinyinStringArray(c2)));
    }
    private String concatPinyinStringArray(String[] pinyinArray) {
        StringBuffer pinyinSbf = new StringBuffer();
        if ((pinyinArray != null) && (pinyinArray.length > 0)) {
            for (int i = 0; i < pinyinArray.length; i++) {
                pinyinSbf.append(pinyinArray[i]);
            }
        }
        return pinyinSbf.toString();
    }
}
