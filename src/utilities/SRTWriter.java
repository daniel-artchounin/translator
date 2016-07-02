package utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import beans.Content;
import beans.ContentPart;


public class SRTWriter {
	public static void writeContent(OutputStream outputStream, Content content) throws UtilitiesException, IOException{
		int i = 0;
		String beginningString = null;
		String endString = null;
		try (Writer outputStreamWriter = new OutputStreamWriter(outputStream)) {
            for( ContentPart contentPart: content.getParts() ){
            	i++;
            	beginningString = contentPart.getBeginning().toString();
            	endString = contentPart.getEnd().toString();
            	outputStreamWriter.write(i + "\n");
            	outputStreamWriter.write( beginningString + ",000 --> " + endString + ",000\n");
            	outputStreamWriter.write(contentPart.getPartContent() + "\n\n");
            }
	    }catch (UnsupportedEncodingException ex) {
	    	throw new UtilitiesException("Action impossible à réaliser, veuillez contacter un administrateur.");
	    }catch (IOException ex) {
	    	throw new UtilitiesException("Action impossible à réaliser, veuillez contacter un administrateur.");
	    }         
	}
}
