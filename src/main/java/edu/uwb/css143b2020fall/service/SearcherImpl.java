package edu.uwb.css143b2020fall.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearcherImpl implements Searcher {
    public List<Integer> search(String keyPhrase, Map<String, List<List<Integer>>> index) {
        //got help from msquared
        String[] words = keyPhrase.split(" ");

        List<Integer> returnList = new ArrayList<>();

        if(words.length <= 1){
            if(index.get(words[0]) == null) {
                return returnList;
            }

            find(index.get(words[0]), returnList);
            return returnList;
        }

        List<List<Integer>> docuLocFirstWord = new ArrayList<>();

        List<List<Integer>> docuLocSecondWord = new ArrayList<>();

        String firstWord = "no input yet";
        String secondWord;
        int count = 0;

        for(String word : words){
            if(count == 0){
                secondWord = null;
            }
            else{
                secondWord = word;
            }
            count++;

            if(firstWord == "no input yet"){
                firstWord = word;
                docuLocFirstWord = index.get(word);
                continue;
            }

            docuLocSecondWord = index.get(word);

            if(!(docuLocFirstWord == null && docuLocSecondWord == null)) {

                if(firstWord.equals(secondWord)){
                    sameWordHelper(docuLocFirstWord, docuLocSecondWord, returnList);
                }

                //not the same
                else if(docuLocFirstWord.size() > 0 && docuLocSecondWord.size() > 0){
                    notSameWordHelper(docuLocFirstWord, docuLocSecondWord, returnList);
                }

                else{
                    return new ArrayList<>();
                }
            }
            //words don't exist
            else{
                return new ArrayList<>();
            }

            docuLocFirstWord.clear();

            for(List<Integer> element : docuLocSecondWord){
                docuLocFirstWord.add(element);
            }
        }
        return returnList;
    }

    //if they are the same word
    private void sameWordHelper(List<List<Integer>> docuOne, List<List<Integer>> docuTwo, List<Integer> sharedLocation) {
        for(int i = 0; i < docuTwo.size(); i++){
            if(docuOne == null) {
                break;
            }

            if(docuOne.get(i).isEmpty() || docuOne.size() == 0) {
                continue;
            }

            List<Integer> locationOne = docuOne.get(i);
            List<Integer> locationTwo = docuTwo.get(i);

            for(int locOneLoop = 0; locOneLoop < locationOne.size(); locOneLoop++){
                for(int locTwoLoop = locOneLoop + 1; locTwoLoop < locationTwo.size() - 1; locTwoLoop++){
                    if(locationOne.get(locOneLoop) == locationTwo.get(locTwoLoop)){
                        if(!sharedLocation.contains(i)) {
                            sharedLocation.add(i);
                        }
                    }

                    else if(sharedLocation.size() > 0){
                        if(sharedLocation.contains(i)){
                            sharedLocation.remove(i);
                        }
                    }
                }
            }
        }
    }

    private void notSameWordHelper(List<List<Integer>> docuLocationOne, List<List<Integer>> docuLocationTwo, List<Integer> sharedLocation){
        for(int i = 0; i < docuLocationTwo.size(); i++) {

            if (docuLocationOne == null){
                if (docuLocationTwo == null) {
                    break;
                }
            break;
        }

            if(docuLocationOne.get(i).size() == 0){
                if(sharedLocation.contains(i)){
                    sharedLocation.remove(i);
                }
                continue;
            }

            List<Integer> indOne = docuLocationOne.get(i);

            if(docuLocationTwo.get(i).size() == 0){
                if(sharedLocation.contains(i)){
                    sharedLocation.remove(Integer.valueOf(i));
                }
                continue;
            }

            List<Integer> indTwo = docuLocationTwo.get(i);

            for(int a = 0; a < indOne.size(); a++){
                for(int b = 0; b < indTwo.size(); b++){
                    if(indOne.get(a) == indTwo.get(b) - 1){
                        if(!sharedLocation.contains(i)) {
                            sharedLocation.add(i);
                        }
                        break;
                    }
                    else if(!sharedLocation.isEmpty()){
                        if(sharedLocation.contains(i)){
                            sharedLocation.remove(Integer.valueOf(i));
                        }
                    }
                }
            }
        }
    }

    //easy way to find if they have a shared document
    private void find(List<List<Integer>> docuLocPos, List<Integer> shared){
        for(int i = 0; i < docuLocPos.size(); i++){

            if(docuLocPos.get(i).size() == 0){
                continue;
            }
            shared.add(i);
        }
    }
}