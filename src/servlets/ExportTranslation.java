package servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Content;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.ExportTranslationDao;
import utilities.SRTWriter;
import utilities.UtilitiesException;


public class ExportTranslation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENTS_MANAGEMENT_PAGE = "/contents_management";

	private ExportTranslationDao exportTranslationDao;
	
    public ExportTranslation() {
    }

    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.exportTranslationDao = daoFactory.getExportTranslationDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action =  request.getParameter("action");
		int contentId = 0;
		int languageId = 0;
		HttpSession session = request.getSession();
		Content content = null;
		if( (action != null) && (action.equals("export_translation") )){
			contentId = Integer.valueOf(request.getParameter("content_id"));
			languageId = Integer.valueOf(request.getParameter("language_id"));
			try {
				content = this.exportTranslationDao.getContent(contentId, languageId);
			} catch (DaoException e) {
				session.setAttribute("errorMessage", e.getMessage());
				response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
			}
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=" + contentId + ".srt");
			
			OutputStream outputStream = response.getOutputStream();
			try {
				SRTWriter.writeContent(outputStream, content);
			} catch (UtilitiesException e) {
				session.setAttribute("errorMessage", e.getMessage());
				response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
			}
			outputStream.flush(); 
			outputStream.close();	

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
