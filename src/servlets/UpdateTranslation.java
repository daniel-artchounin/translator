package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOConfigurationException;
import dao.DaoFactory;
import dao.UpdateTranslationDao;


public class UpdateTranslation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPDATE_TRANSLATION_JSP = "/WEB-INF/update_translation.jsp";

	private UpdateTranslationDao updateTranslationDao;
	
    public UpdateTranslation() {
    }
    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.updateTranslationDao = daoFactory.getUpdateTranslationDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(UPDATE_TRANSLATION_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
