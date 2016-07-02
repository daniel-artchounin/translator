package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOConfigurationException;
import dao.DaoException;
import dao.DaoFactory;
import dao.ExportTranslationDao;
import dao.TranslationManagementDao;


public class TranslationManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String EXPORT_TRANSLATION_JSP = "/WEB-INF/export_translation.jsp";
	private static final String CONTENTS_MANAGEMENT_PAGE = "/contents_management";

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
				break;
			case "deactivate_translation":
				System.out.println("deactivate_translation");
				break;
			case "consult_modify_translation":
				System.out.println("consult_modify_translation");
				break;	
			default:
				
			}
		}
		response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
