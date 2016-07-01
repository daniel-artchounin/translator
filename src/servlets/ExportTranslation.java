package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOConfigurationException;
import dao.DaoFactory;
import dao.ExportTranslationDao;


public class ExportTranslation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String EXPORT_TRANSLATION_JSP = "/WEB-INF/export_translation.jsp";

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
		this.getServletContext().getRequestDispatcher(EXPORT_TRANSLATION_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
