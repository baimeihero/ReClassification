/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iscas.util.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to read documents
 *
 * @author Mubin Shrestha
 */
public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
            }
            
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("parseFiles:"+df.format(new Date()));// new Date()为获取当前系统时间
    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency 
        int i=0;
        for (String[] docTermsArray : termsDocsArray) {
        	System.out.println(i+++"/"+termsDocsArray.size());
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
   		System.out.println("tfIdfCalculator:"+df.format(new Date()));// new Date()为获取当前系统时间
    }

    /**
     * Method to calculate cosine similarity between all the documents.
     */
    public void getCosineSimilarity() {
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
        	System.out.println(i);
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                System.out.println("between " + i + " and " + j + "  =  "
                                   + new CosineSimilarity().cosineSimilarity
                                       (
                                         tfidfDocsVector.get(i), 
                                         tfidfDocsVector.get(j)
                                       )
                                  );
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
      		System.out.println("getCosineSimilarity:"+df.format(new Date()));// new Date()为获取当前系统时间
    }
}
