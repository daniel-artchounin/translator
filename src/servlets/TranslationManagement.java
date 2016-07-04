package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Content;
import beans.Language;
import beans.PartTranslation;
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
		ArrayList<Language> activatedLanguages = null;
		Content content = null;
		String json = null;
		System.out.println("***********");
		if(action != null){
			System.out.println(request.getParameter("language_id"));
			contentId = Integer.valueOf(request.getParameter("content_id"));
			languageId = Integer.valueOf(request.getParameter("language_id"));
			switch (action) {
			case "activate_translation":		
				try {
					this.translationManagementDao.activateTranslation(contentId, languageId);
					session.setAttribute("successMessage", "Action effectuée avec succès");
				} catch (DaoException e) {
					session.setAttribute("errorMessage", e.getMessage());
				}
				response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
				break;
			case "deactivate_translation":
				try {
					this.translationManagementDao.deactivateTranslation(contentId, languageId);
					session.setAttribute("successMessage", "Action effectuée avec succès");
				} catch (DaoException e) {
					session.setAttribute("errorMessage", e.getMessage());
				}
				response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
				break;
			case "change_translation":
				try {
					content = this.translationManagementDao.getContent(contentId, languageId);
					Gson gson = new Gson();
					json = gson.toJson(content);
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(json);
				} catch (DaoException e) {
					session.setAttribute("errorMessage", e.getMessage());
					response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
				}
				break;
			case "update_translation":
				int numberOfDeactivatedLanguageParts = Integer.valueOf(request.getParameter("number_of_deactivated_language_parts"));
				String deactivatedLanguagePartContent = null;
				int deactivatedLanguagePartId = 0;
				ArrayList<PartTranslation> partTranslations = new ArrayList<PartTranslation>();
				int chosenLanguageId = Integer.valueOf(request.getParameter("languageId"));
				for(int i=1; i<=numberOfDeactivatedLanguageParts; i++){
					String parameter1 = "deactivated_language_part_content_" + i;
					String parameter2 = "deactivated_language_part_id_" + i;
					deactivatedLanguagePartContent = request.getParameter(parameter1);
					deactivatedLanguagePartId = Integer.valueOf(request.getParameter(parameter2));
					partTranslations.add(new PartTranslation(deactivatedLanguagePartId, languageId, deactivatedLanguagePartContent));
				}
				try {
					this.translationManagementDao.updateTranslation(partTranslations);
					request.setAttribute("deactivatedTranslation", this.translationManagementDao.getContent(contentId, languageId));
					activatedLanguages = this.translationManagementDao.getContentActivatedLanguages(contentId);
					request.setAttribute("activatedLanguages", activatedLanguages);		
		            for( Language activatedLanguage : activatedLanguages ){
		            	if(activatedLanguage.getId() == chosenLanguageId )
		            		request.setAttribute("chosenLanguage", activatedLanguage);
		            		break;
		            }					
					request.setAttribute("deactivatedLanguage", languageId);
					request.setAttribute("contentId", contentId);
					request.setAttribute("activatedTranslation", this.translationManagementDao.getContent(contentId, chosenLanguageId));
					this.getServletContext().getRequestDispatcher( MODIFY_TRANSLATION_JSP ).forward(request, response);
				} catch (DaoException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
				this.getServletContext().getRequestDispatcher( MODIFY_TRANSLATION_JSP ).forward(request, response);
				break;
			case "consult_modify_translation":
				try {
					modifiableTranslation = this.translationManagementDao.isModifiableTranslation(contentId, languageId);
					if( modifiableTranslation ){						
						request.setAttribute("deactivatedTranslation", this.translationManagementDao.getContent(contentId, languageId));
						activatedLanguages = this.translationManagementDao.getContentActivatedLanguages(contentId);
						request.setAttribute("activatedLanguages", activatedLanguages);
						request.setAttribute("chosenLanguage", activatedLanguages.get(0));
						request.setAttribute("deactivatedLanguage", languageId);
						request.setAttribute("contentId", contentId);
						request.setAttribute("activatedTranslation", this.translationManagementDao.getContent(contentId, activatedLanguages.get(0).getId()));
						this.getServletContext().getRequestDispatcher( MODIFY_TRANSLATION_JSP ).forward(request, response);
					} else {
						request.setAttribute("content", this.translationManagementDao.getContent(contentId, languageId));
						this.getServletContext().getRequestDispatcher( CONSULT_TRANSLATION_JSP ).forward(request, response);
					}					
				} catch (DaoException e) {					
					session.setAttribute("errorMessage", e.getMessage());
					response.sendRedirect( request.getContextPath() + CONTENTS_MANAGEMENT_PAGE ); // Redirection
				}				
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
