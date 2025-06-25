package com.music.demo.search.util;

import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.Iterator;
import java.util.List;

public class WordsUtil {
    private static WordsUtil instance;
    JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();

    private WordsUtil() {}

    public static WordsUtil getInstance() {
        if (instance == null) {
            instance = new WordsUtil();
        }
        return instance;
    }

    public List<String> word(String sentence) {
        List<String> list = jiebaSegmenter.sentenceProcess(sentence);
        Iterator<String> it = list.iterator();

        while (it.hasNext()) {
            String word = it.next();
            if (word.matches("[你我他啊呀，]")){
                it.remove();
            }
        }
        return list;
     }
}
