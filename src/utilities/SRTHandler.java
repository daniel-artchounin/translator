package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Part;

import beans.Content;
import beans.ContentPart;

/*
 * This class allows us to initialize a Content bean using a SRT file.
 */
public class SRTHandler {
	public static Content getContent(Part filePart, String contentName) throws UtilitiesException{
		Content content = new Content(contentName);
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		ArrayList<ContentPart> contentParts = new ArrayList<ContentPart>();
		String partContent = null;
		boolean partFirstLine = true;
		try {
			inputStream = filePart.getInputStream();
		} catch (IOException e) {
			throw new UtilitiesException("Fichier de sous-titres non valide");
		}		
		try (Reader inputStreamReader = new InputStreamReader(inputStream)) {
			bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			String beginningEndLinePattern = "^(\\d+:\\d\\d:\\d\\d,\\d\\d\\d) --> (\\d+:\\d\\d:\\d\\d,\\d\\d\\d)";
			Pattern beginningEndLineCPattern = Pattern.compile(beginningEndLinePattern);
			while( ( line = bufferedReader.readLine() ) != null ){
				if( line.length() == 0 ){
					/* We have reached the end of the file */
					break;
				}
		        if( !Pattern.matches("^\\d+$", line) ){
		        	/* The line doesn't contain the id of the part */
		        	throw new UtilitiesException("Fichier de sous-titres non valide");
		        }
		        line = bufferedReader.readLine();
        		if( line == null ){
        			/* The file doesn't contain any other line */
        			throw new UtilitiesException("Fichier de sous-titres non valide");
        		} else {
        			/* The file contain another line */
        			Matcher m = beginningEndLineCPattern.matcher(line);
					if (!m.find( )) {
						/* This line is not valid */
						throw new UtilitiesException("Fichier de sous-titres non valide");
					}
					partContent = null;
					partFirstLine = true;
					while( ( line = bufferedReader.readLine() ) != null ){
						/* Here, we read the content of the part */
						if( line.length() == 0 ){
							/* We have reached the end of the part */
							break;
						} else {
							if ( partFirstLine ){
								partContent = line;
								partFirstLine = false;
							} else {
								partContent += "\n";
								partContent += line;
							}							
						}						
					}	
					ContentPart contentPart = new ContentPart(m.group(1), m.group(2), partContent);
					contentParts.add( contentPart );
        		}        		
			}
			content = new Content(contentName, contentParts);
	    }catch (UnsupportedEncodingException ex) {
	    	throw new UtilitiesException("Fichier de sous-titres non valide");
	    }catch (IOException ex) {
	    	throw new UtilitiesException("Fichier de sous-titres non valide");
	    }
		return content;
	}
}
