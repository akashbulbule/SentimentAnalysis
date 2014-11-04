package org.myorg;
import org.myorg.*;
import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

/*
 * 	This a Driver Class which controls all the map reduce operations
 */

public class MainDriver {
		
		public static void main(String[] args) throws Exception{
		//---------------   Bucket Creation ---------------------------
		
		// This part executes MapReduce tasks for generating base rank and refrences
		JobConf conf = new JobConf(Index.class);
		conf.setJobName("Bucketing_the_reviews");
		FileSystem fs = FileSystem.get(conf);
		
		// paths for input, output and intermediate paths
		Path RawDataInPath = new Path(args[0]);// Path where the orignal raw data is stored
		Path IndexOutPath = new Path(args[1]);// Output the result to ~/QueryIndex/output
		//Path IntrPath = new Path("/user/cloudera/QueryIndex/output");// Use this for intermediate paths
		//Path INTR1Path = new Path("/user/cloudera/PageRank/output");
		
		// clean up the contents for these paths
		if(fs.exists(IndexOutPath)) fs.delete(IndexOutPath, true);
		//if(fs.exists(IntrPath))fs.delete(GraphPath, true);
		//if(fs.exists(INTR1Path))fs.delete(PageRankPath, true);
		
		//Set up the classes for jobs
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		conf.setMapperClass(Index.Map.class);
		conf.setReducerClass(Index.Reduce.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		// Set up the paths
		FileInputFormat.setInputPaths(conf, RawDataInPath);// In put is from args[0]
		FileOutputFormat.setOutputPath(conf, IndexOutPath);// In put from arg[1]
		
		// Build the link graph
		System.out.println("\n---------------------------");
		System.out.println( " Creating buckets ");
		System.out.println("input taken from = " + RawDataInPath.toString());
		System.out.println("output to = " + IndexOutPath.toString());
		System.out.println( "---------------------------");
		
		JobClient.runJob(conf);

	}
}
