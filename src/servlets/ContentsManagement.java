package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.ContentsManagementDao;


public class ContentsManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENTS_MANAGEMENT_JSP = "/WEB-INF/contents_management.jsp";

	private ContentsManagementDao contentsManagementDao;
	
    public ContentsManagement() {
    }
    
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.contentsManagementDao = daoFactory.getUpdateTranslationDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action =  request.getParameter("action");
		int contentId = 0;
		if( (action != null) && (action.equals("delete_content")) ){
			contentId = Integer.valueOf(request.getParameter("content_id"));
			try {
				this.contentsManagementDao.deleteContent(contentId);
				request.setAttribute("successMessage", "Le contenu vient d'être supprimé avec succès");
			} catch (DaoException e) {
				request.setAttribute("errorMessage", e.getMessage());
			}
		}
		try {
			request.setAttribute("contents", this.contentsManagementDao.getContents());
		} catch (DaoException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		this.getServletContext().getRequestDispatcher(CONTENTS_MANAGEMENT_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
