import os

def main():
	print "\n\n"
	print "**** Welome to Sentiment Analysis for Movie Reviews  ****"
	print "\n\n"
	print "Setting up hdfs for input and output ....\n"
	os.system("sh config/setup_dir.sh")
	print "\nhdfs input/output is now setup."
	print "\nSetting up compliation envirornment..."
	os.system("sh config/com_setup.sh\n")
	print "\ncompling MainDriver.java..\n"
	os.system("sudo javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop/client-0.20/* -d Index_classes Index.java  MainDriver.java")
	os.system("jar -cvf MainDriver.jar -C Index_classes/ .")
	print "\nJar created.."
	print "\nExecuting jar on Hadoop...\n\n"
	os.system("sudo -u hdfs hadoop jar MainDriver.jar org.myorg.MainDriver /user/cloudera/QueryIndex/input /user/cloudera/QueryIndex/output")
	print "\n\n getting the response.."
	os.system("sudo rm -rf output")
	os.system("sudo mkdir output")
	os.system("sudo hadoop fs -get /user/cloudera/QueryIndex/output")
if __name__ == "__main__":
	main()