package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexerImpl implements Indexer {
    public Map<String, List<List<Integer>>> index(List<String> docs) {
        Map<String, List<List<Integer>>> indexes = new HashMap<>();

        List<List<Integer>> doc = new ArrayList<>();

        List<String> goodStuff = new ArrayList<>();

        for(String docNum : docs){
            docNum.replaceAll("", " ");
            String[] words = docNum.split(" ");

            for(String word : words){
                if(word.length() == 0){
                    continue;
                }
                unique(word, goodStuff);
            }
        }

        for(String word : goodStuff){
            if(word.length() == 0){
                continue;
            }
            for(String docName : docs){
                doc.add(pos(word, docName));
            }
            indexes.put(word, new ArrayList<>(doc));
            doc.clear();
        }
        return indexes;
    }

    //helpers
    private void unique(String word, List<String> l){
        if(l.size() == 0){
            l.add(word);
        }
        else{
            if(!l.contains(word)){
                l.add(word);
            }
        }
    }

    private List<Integer> pos(String lookingFor, String numOfDoc){
        List<Integer> result = new ArrayList<>();
        List<Integer> notFun = new ArrayList<>();

        String[] w = numOfDoc.split(" ");
        int count = 0;

        for(int i = 0; i < w.length; i++){
            if(w[i].length() == 0){
                count++;
            }

            if(lookingFor.equals(w[i])){
                result.add(i - count);
            }
        }

        if(result.size() == 0){
            return notFun;
        }
        return result;
    }
}