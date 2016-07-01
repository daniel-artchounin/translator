package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.Content;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.ImportContentDao;
import utilities.SRTHandler;
import utilities.SRTHandlerException;


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
		try {
			request.setAttribute("languages", this.importContentDao.getLanguages());
		} catch (DaoException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher(IMPORT_CONTENT_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Part filePart = request.getPart("file");
		int languageId = Integer.valueOf(request.getParameter("languageId"));
		Content content = null;
		try {
			content = SRTHandler.getContent(filePart, request.getParameter("contentName"));
		} catch (SRTHandlerException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		doGet(request, response);
	}

}
