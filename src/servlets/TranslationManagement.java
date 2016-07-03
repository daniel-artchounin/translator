package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Content;
import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.TranslationManagementDao;


public class TranslationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENTS_MANAGEMENT_PAGE = "/contents_management";
	private static final String CONSULT_TRANSLATION_JSP = "/WEB-INF/consult_translation.jsp";
	private static final String MODIFY_TRANSLATION_JSP = "/WEB-INF/modify_translation.jsp";

	private TranslationManagementDao translationManagementDao;
	
    public TranslationManagement() {
    }

    public void init() throws ServletException {
        DaoFactory daoFactory;
		try {
			daoFactory = DaoFactory.getInstance();
			this.translationManagementDao = daoFactory.getTranslationManagementDao();
		} catch (DAOConfigurationException e) {
			e.printStackTrace();
		}        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action =  request.getParameter("action");
		int contentId = 0;
		int languageId = 0;
		HttpSession session = request.getSession();
		boolean modifiableTranslation = false;
		Content content = null;
		if(action != null){
			contentId = Integer.valueOf(request.getParameter("content_id"));
			languageId = Integer.valueOf(request.getParameter("language_id"));
			switch (action) {
			case "activate_translation":		
				// System.out.println("activate_translation"); // Test
				try {
					this.translationManagementDao.activateTranslation(contentId, languageId);
					session.setAttribute("successMessage", "Action effectuée avec succès");
				} catch (DaoException e) {
					session.setAttribute("errorMessage", e.getMessage());
				}
				response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
				break;
			case "deactivate_translation":
				// System.out.println("deactivate_translation"); // Test
				try {
					this.translationManagementDao.deactivateTranslation(contentId, languageId);
					session.setAttribute("successMessage", "Action effectuée avec succès");
				} catch (DaoException e) {
					session.setAttribute("errorMessage", e.getMessage());
				}
				response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
				break;
			case "consult_modify_translation":
				// System.out.println("consult_modify_translation"); // Test
				try {
					modifiableTranslation = this.translationManagementDao.isModifiableTranslation(contentId, languageId);
					System.out.println(modifiableTranslation);
					if( modifiableTranslation ){
						this.getServletContext().getRequestDispatcher( MODIFY_TRANSLATION_JSP ).forward(request, response);
					} else {
						content = this.translationManagementDao.getContent(contentId, languageId);
						this.getServletContext().getRequestDispatcher( CONSULT_TRANSLATION_JSP ).forward(request, response);
					}
					session.setAttribute("successMessage", "Action effectuée avec succès");
				} catch (DaoException e) {					
					session.setAttribute("errorMessage", e.getMessage());
				}
				
				break;	
			default:
				
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
