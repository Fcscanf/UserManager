package com.qst.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logsave {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 public void logMsg(File logFile,String mesInfo) throws IOException{  
	        if(logFile == null) {  
	            throw new IllegalStateException("logFile can not be null!");  
	        }  
	        Writer txtWriter = new FileWriter(logFile,true);  
	        txtWriter.write("本地操作日志"+dateFormat.format(new Date()) +"\t"+mesInfo+"\n");
	        txtWriter.flush();  
	    }  
}
