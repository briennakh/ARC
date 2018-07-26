package ARC;

import java.util.*;

/**
 * Document clustering
 */
public class Clustering {
	int k; // num of clusters
	ArrayList<String[]> tokenizedDocs;
	ArrayList<Doc> vectorSpace;
	ArrayList<String> vocabulary;
	
	/**
	 * Constructor for attribute initialization
	 * @param numC number of clusters
	 */
	public Clustering(int numC) {
		k = numC;
	}
	
	/**
	 * Calculate term frequency for given term in a document
	 * @param tokens other terms in the document to use in calculation
	 * @param term the given term
	 * @return term frequency
	 */
	private double tf(String[] tokens, String term) {
		double n = 0; 
		for (String s : tokens) {
			if (s.equalsIgnoreCase(term)) {
				n++;
			}
		}
		return n / tokens.length;
	}
	
	/**
	 * Calculate inverted document frequency for given term
	 * @param term given term
	 * @return inverted document frequency
	 */
	private double idf(String term) {
		double n = 0; 
		for (String[] tokens : tokenizedDocs) {
			for (String s : tokens) {
				if (s.equalsIgnoreCase(term)) {
					n++;
					break;
				}
			}
		}
		return Math.log(tokenizedDocs.size() / n);
	}
	
	/**
	 * Calculate tf-idf for given term 
	 * @param tokens to be used in tf calculation
	 * @param term the given term
	 * @return tf-idf
	 */
	private double findTFIDF(String[] tokens, String term) {
		double tf = tf(tokens, term);
		double idf = idf(term);
		return tf * idf;
	}
	
	/**
	 * Calculate cosine similarity between two vectors
	 * @param vector1 
	 * @param vector2
	 * @return cosine similarity
	 */
	private double getSimilarity(double[] vector1, double[] vector2) {
		double dotProduct = 0.0;
		double norm1 = 0.0;
		double norm2 = 0.0;
		double cosineSimilarity = 0.0;
		
		for (int i = 0; i < vector1.length; i++) { // vectors are of same length
			dotProduct += vector1[i] * vector2[i]; // a * b
			norm1 += Math.pow(vector1[i], 2); // a^2
			norm2 += Math.pow(vector2[i], 2); // b^2
		}
		
		norm1 = Math.sqrt(norm1); // sqrt(a^2)
		norm2 = Math.sqrt(norm2); // sqrt(b^2)
		
		if (norm1 != 0.0 | norm2 != 0.0) {
			cosineSimilarity = dotProduct / (norm1 * norm2);
		} else {
			// keep cosineSimilarity at 0.0
		}
		
		return cosineSimilarity;
	}
	
	
	/**
	 * Load the documents to build the vector representations
	 * @param docs
	 */
	public void preprocess(String[] docs){
		tokenizedDocs = new ArrayList<String[]>();
		vectorSpace = new ArrayList<Doc>();
		vocabulary = new ArrayList<String>();
		
		// For each document,
		for (int i = 0; i < docs.length; i++) {
			// Tokenize document, removing punctation
			String[] tokens = docs[i].split("[ _\".,?!/:;$%&*+()\\-\\^]+");
			tokenizedDocs.add(i, tokens);
			
			// Add new terms to vocabulary
			for (String token : tokens) {
				if (!vocabulary.contains(token)) {
					vocabulary.add(token);
				}
			}
		}
		
		// Calculate tf*idf of each document
		for (int i = 0; i < docs.length; i++) {
			String[] doc = tokenizedDocs.get(i);
			double[] weights = new double[vocabulary.size()]; // all doc vectors are same length, dimensions equal to size of vocabulary
			int count = 0;
			// For each term in the document
			for (String term : vocabulary) {
				double tfidf = findTFIDF(doc, term);
				weights[count] = tfidf;
				count++;
			}
			
			Doc d = new Doc(i, weights);
			vectorSpace.add(i, d);
		}
	}
	
	/**
	 * Cluster the documents
	 * For k-means clustering, use the first and the ninth documents as the initial centroids
	 */
	public void cluster(){
		// Select random seeds (here we use 1st and 9th documents)
		// cluster w
		// centroid μ
		// num of cluster k
		
		// Doc initialCentroid = myDocs[1];
		double[] centroid1 = vectorSpace.get(0).weights;
		double[] centroid2 = vectorSpace.get(8).weights;
		
		// Get distances between all documents (to test)
		for (int i = 0; i < vectorSpace.size(); i++) {
			for (int j = 0; j < vectorSpace.size(); j++) {
				System.out.println("between " + i + " and " + j + " = " +
						String.format("%1.5f", getSimilarity(vectorSpace.get(i).weights, vectorSpace.get(j).weights)));
			}
		}
				
		// For all clusters?
		for (int i = 0; i < k; i++) {
			
		}
		
 	}
	
	public String toString() {
		String matrixString = new String();
		for (int i = 0; i < vectorSpace.size(); i++) {
			matrixString += vectorSpace.get(i).toString() + "\n";
		}
		return matrixString;
	}
	
	/** 
	 * To help test algorithm on this small dataset
	 */
	public static void main(String[] args){
		String[] docs = {"hot chocolate cocoa beans",
				 "cocoa ghana africa",
				 "beans harvest ghana",
				 "cocoa butter",
				 "butter truffles",
				 "sweet chocolate can",
				 "brazil sweet sugar can",
				 "suger can brazil",
				 "sweet cake icing",
				 "cake black forest"
				};
		
		Clustering c = new Clustering(2);
		c.preprocess(docs);
		System.out.println(c);
		c.cluster();
		/*
		 * Expected result:
		 * Cluster: 0
			0	1	2	3	4	
		   Cluster: 1
			5	6	7	8	9	
		 */
	}
}

/**
 * Document class for the vector representation of a document
 */
class Doc{
	int id;
	String[] terms;
	double[] weights; 

	public Doc(int id, double[] weights) {
		this.id = id;
		this.weights = weights;
	}
	
	public String toString() {
		String matrixString = new String();
		matrixString += "Doc " + id + ": <";
		for (int i = 0; i < weights.length; i++) {
			matrixString += String.format("%4.3f", weights[i]) + ", ";
		}
		matrixString += ">";
		return matrixString;
	}
}


