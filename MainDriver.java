
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
		
		// String[] bucket_names = ;
		 //bucket_names[0] = "one";
		 //bucket_names[1] = "two";
		 //bucket_names[2] = "three";
		 //bucket_names[3] = "four";
		 String output_path = args[1];
		Index job1 = new Index();
		Path TempOutputPath = null;
		for(int i = 0; i <= 4;i++ ){
			//---------------   Bucket Creation ---------------------------
		
			// This part executes MapReduce tasks for generating base rank and refrences
			job1.runjob(args[0], args[1], i);
		
		//-------------------------------------------------------------
		}
	}
}
