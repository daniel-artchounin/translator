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


/* The servlet used to manage the contents. */
public class ContentsManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENTS_MANAGEMENT_JSP = "/WEB-INF/contents_management.jsp";

	private ContentsManagementDao contentsManagementDao;
	
    public ContentsManagement() {
    }
    
    /* We instantiate an implementation to interact with our data structure. */
    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.contentsManagementDao = daoFactory.getContentsManagementDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action =  request.getParameter("action");
		int contentId = 0;
		String contentName = null;
		if( (action != null) && (action.equals("delete_content")) ){
			/* Here, we should delete a content */
			contentName = request.getParameter("content_name");
			contentId = Integer.valueOf(request.getParameter("content_id"));
			try {
				this.contentsManagementDao.deleteContent(contentId);
				request.setAttribute("successMessage", "Le contenu " + contentName + " vient d'être supprimé avec succès.");
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
