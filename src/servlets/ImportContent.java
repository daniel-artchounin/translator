package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.DAOConfigurationException;
import dao.DaoFactory;
import dao.ImportContentDao;


public class ImportContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMPORT_CONTENT_JSP = "/WEB-INF/import_content.jsp";

	private ImportContentDao importContentDao;
	
    public ImportContent() {
    }

    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.importContentDao = daoFactory.getImportContentDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(IMPORT_CONTENT_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Part filePart = request.getPart("file");
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		inputStream = filePart.getInputStream();		
		try (Reader inputStreamReader = new InputStreamReader(inputStream)) {
			bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while((line = bufferedReader.readLine()) != null){
				System.out.println(line);
			}
	    }catch (UnsupportedEncodingException ex) {
	       
	    }catch (IOException ex) {
	       
	    }
		doGet(request, response);
	}

}
