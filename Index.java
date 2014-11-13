package org.myorg;

import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 *	Build Buckets
 *	Output: 4 files
 * 	
 */

public class Index{
	
	public static String bucketid;

    	public void configure(JobConf job) {
        	//super.configure(job);
        	bucketid = job.get("bucketid"); 
   	 }	

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
		private Text sentence = new Text();
		private Text sentiment  = new Text();
		private String str = null;
		public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,Reporter reporter)throws IOException{
			str = get_senti(value.toString());
			sentence.set(value.toString());
			sentiment.set(str);
			if(str == bucketid) output.collect(sentence, sentiment);
		}	
	}

	
	//Reducer just collects
	public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
		public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
			if(values.hasNext()) output.collect(key, values.next());
			else throw new IOException("No links found for" + key.toString());
		}
	}

	public static String get_senti(String str){
		String[] columnDetail = new String[11];
		columnDetail = str.split("\t");
		return columnDetail[3];
	}
	

}
