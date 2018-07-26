package ARC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Parser {
    
    public String[] myDocs;
    public ArrayList<String> termList;
    private String[] stopList ;
    public ArrayList<Integer> termFrequency;
    public ArrayList<ArrayList<Integer>> docLists;
    public Data data;

    public Parser(){
    	data = new Data();
        data.init();
        data.showSomeData();
        String content= data.content;
        //System.out.println(content);
        
        termList = new ArrayList<String>();
        termFrequency = new ArrayList<Integer>();
        //load stopwords and sort
        //File test = new File("index.html");
        //System.out.println(test.listFiles());
        stopList = loadStopwords("src/ARC/input/stopwords.txt");
        Arrays.sort(stopList);
        
        ArrayList<String> terms = tokenization(content);
        System.out.println("No.of terms: "+ Integer.toString(terms.size()));
        sortTerms(terms);

        // Begin clustering
        Clustering c = new Clustering(2);
        System.out.println("\n" + data.getDocs().length);
		c.preprocess(data.getDocs());
		System.out.println(c);
		// c.cluster();
    }

    public String[] loadStopwords(String stopwordsFile){

        String[] stopWords = null;
        String alllines= "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader(stopwordsFile));
            String allLines = new String();
            String line = null;
            while((line=reader.readLine())!=null){
                allLines += line.toLowerCase()+"\n"; //case folding
            }
            stopWords = allLines.split("\n");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return stopWords;
    }


    public int searchStopword(String key)
    {
		/*
		  Search if key is present in the stopwordList using binarySearch
		  (stopwordList should be sorted)
		 */
        int lo = 0;
        int hi = stopList.length-1;
        while(lo<=hi)
        {
            //Key is in a[lo..hi] or not present
            int mid = lo + (hi-lo)/2;
            int result = key.compareTo(stopList[mid]);
            if(result <0) hi = mid - 1;
            else if(result >0) lo = mid+1;
            else return mid;
        }
        return -1;
    }

    public String stemming(String token){
        Stemmer st = new Stemmer();
        st.add(token.toCharArray(), token.length());
        st.stem();
        return st.toString();
    }

    public void sortTerms(ArrayList<String> terms) {
        ArrayList<Node> listNode = new ArrayList<>();

        for (String term : terms){
            int index = termList.indexOf(term);
            int tf = termFrequency.get(index);
            Node node = new Node(tf, term);
            listNode.add(node);
            Collections.sort(listNode, (a, b) -> a.freq - b.freq);
        }

        for(int k=0; k<listNode.size(); k++){
            String s= (listNode.get(k)).word;
            Integer tf = ((listNode.get(k)).freq);
            System.out.println(s+"\t"+ Integer.toString(tf));
        }

    }


    public ArrayList<String> tokenization(String review){

        /*
		   Tokenization: Creates an array of tokens
		 */
        String[] tokens = null;
        review  = review.toLowerCase();
        tokens  = review.split("[ .,&%$#!/+()-*^?:\"--]+");
        System.out.println("No.of tokens: "+ tokens.length);
        for (String token : tokens) {

            if (searchStopword(token) == -1 && token.length()!=1){
                token = stemming(token);
                //System.out.println("token: "+token);
                if (!termList.contains(token)) {//a new term
                    termList.add(token);
                    termFrequency.add(1);
                    /*
                    //docList = new ArrayList<Integer>();
                    //docList.add(i);
                    //docLists.add(docList);
                    */

                } else {//an existing term
                    int index = termList.indexOf(token);
                    int tf = termFrequency.get(index);
                    tf++;
                    termFrequency.set(index, tf);
                    }
                }
            }
        return termList;
        }


    public static void main(String[] args) {
    	Parser p = new Parser(); 
    }

}
