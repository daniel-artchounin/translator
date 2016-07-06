package utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import beans.Content;
import beans.ContentPart;


/*
 * This class allows us to write a SRT file using a Content bean.
 */
public class SRTWriter {
	public static void writeContent(OutputStream outputStream, Content content) throws UtilitiesException, IOException{
		int i = 0;
		String beginningString = null;
		String endString = null;
		String partContent = null;
		try (Writer outputStreamWriter = new OutputStreamWriter(outputStream)) {
            for( ContentPart contentPart: content.getParts() ){
            	i++;
            	beginningString = contentPart.getBeginning().toString();
            	endString = contentPart.getEnd().toString();
            	outputStreamWriter.write(i + "\n");
            	outputStreamWriter.write( beginningString + " --> " + endString + "\n");
            	partContent = contentPart.getPartContent();
            	if(partContent.length() == 0){
            		outputStreamWriter.write("\n");
            	}else{
            		outputStreamWriter.write(partContent + "\n\n");
            	}            	
            }
	    }catch (UnsupportedEncodingException ex) {
	    	throw new UtilitiesException("Action impossible à réaliser, veuillez contacter un administrateur.");
	    }catch (IOException ex) {
	    	throw new UtilitiesException("Action impossible à réaliser, veuillez contacter un administrateur.");
	    }         
	}
}
