package com.course.management.portlet;

import com.ats.courses.model.Courses;

import com.ats.courses.service.CoursesLocalServiceUtil;
import com.course.management.constants.HomeControllerPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Personal
 */
@Component(
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.header-portlet-css=/css/main.css",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=HomeController",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=" + HomeControllerPortletKeys.HOMECONTROLLER,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class HomeControllerPortlet extends MVCPortlet {
	
	
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) 
	        throws IOException, PortletException {
	    String coursesType = ParamUtil.getString(renderRequest, "coursesType");
	    //System.out.println("seletect valeus ::"+coursesType);
	    List<Courses> coursesList;
	    
	    if (Validator.isNotNull(coursesType)) {
	        coursesList = CoursesLocalServiceUtil.GetAllCoursesTypes(coursesType);
	    } else {
	        coursesList = CoursesLocalServiceUtil.getCourseses(-1, -1);
	    }
	    
	    renderRequest.setAttribute("coursesList", coursesList);
	    renderRequest.setAttribute("selectedCourseType", coursesType);
	    super.doView(renderRequest, renderResponse);
	}
	
	
	public void addCourses(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		System.out.println("addCourses method inside :::::::::::::::::::::::");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try {
		long groupId = themeDisplay.getScopeGroupId();
		long companyId = themeDisplay.getCompanyId();
		long userId = themeDisplay.getUserId();
		String userName = "ats";
		String Courses_name = ParamUtil.getString(actionRequest, "Courses_name");
		String Courses_des = ParamUtil.getString(actionRequest, "Courses_des");
		String Courses_type = ParamUtil.getString(actionRequest, "Courses_type");
		String Courses_rating = ParamUtil.getString(actionRequest, "Courses_rating");

		UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		InputStream CoursesImgStream = uploadPortletRequest.getFileAsStream("Courses_img");

		CoursesLocalServiceUtil.addCourses(Courses_name, Courses_des, Courses_type, Courses_rating, CoursesImgStream);
	
		   
		    SessionMessages.add(actionRequest, "course-added-success");
		} catch (Exception e) {
		    SessionErrors.add(actionRequest, "course-added-error");
		   e.getStackTrace();
		}
	 actionResponse.sendRedirect(PortalUtil.getCurrentURL(actionRequest));
	}
	
}