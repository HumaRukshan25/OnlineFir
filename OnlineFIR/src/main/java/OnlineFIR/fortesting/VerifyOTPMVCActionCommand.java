//package OnlineFIR.fortesting;
//
//import com.liferay.portal.kernel.model.User;
//import com.liferay.portal.kernel.service.UserLocalServiceUtil;
//import com.liferay.portal.kernel.service.ServiceContext;
//import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
//import com.liferay.portal.kernel.util.ParamUtil;
//import com.liferay.portal.kernel.util.PortalUtil;
//
//import org.osgi.service.component.annotations.Component;
//
//import OnlineFIR.constants.OnlineFIRPortletKeys;
//
//import javax.portlet.ActionRequest;
//import javax.portlet.ActionResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.Enumeration;
//
//@Component(
//    immediate = true,
//    property = {
//        "javax.portlet.name=" + OnlineFIRPortletKeys.ONLINEFIR,
//        "mvc.command.name=/user/verifyOtp"
//    },
//    service = MVCActionCommand.class
//)
//public class VerifyOTPMVCActionCommand implements MVCActionCommand {
//
//    @Override
//    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) {
//        try {
//            System.out.println("=== OTP VERIFICATION STARTED ===");
//            
//            // Get the HTTP request and session
//            HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(actionRequest);
//            HttpSession session = httpRequest.getSession();
//            
//            // Debug: Print all session attributes
//            System.out.println("=== SESSION ATTRIBUTES ===");
//            Enumeration<String> sessionAttrNames = session.getAttributeNames();
//            while (sessionAttrNames.hasMoreElements()) {
//                String attrName = sessionAttrNames.nextElement();
//                Object attrValue = session.getAttribute(attrName);
//                System.out.println(attrName + " = " + attrValue);
//            }
//            
//            // Debug: Print all action request parameters
//            System.out.println("=== ACTION REQUEST PARAMETERS ===");
//            Enumeration<String> actionParamNames = actionRequest.getParameterNames();
//            while (actionParamNames.hasMoreElements()) {
//                String paramName = actionParamNames.nextElement();
//                String paramValue = actionRequest.getParameter(paramName);
//                System.out.println(paramName + " = '" + paramValue + "'");
//            }
//            
//            // Debug: Print all HTTP request parameters
//            System.out.println("=== HTTP REQUEST PARAMETERS ===");
//            Enumeration<String> httpParamNames = httpRequest.getParameterNames();
//            while (httpParamNames.hasMoreElements()) {
//                String paramName = httpParamNames.nextElement();
//                String paramValue = httpRequest.getParameter(paramName);
//                System.out.println(paramName + " = '" + paramValue + "'");
//            }
//            
//            // Try different ways to get the OTP parameter
//            String enteredOtp = null;
//            
//            // Method 1: Try with namespace
//            String namespace = actionRequest.getParameter("namespace");
//            if (namespace != null) {
//                enteredOtp = actionRequest.getParameter(namespace + "otp");
//                System.out.println("OTP with namespace: '" + enteredOtp + "'");
//            }
//            
//            // Method 2: Try ParamUtil
//            if (enteredOtp == null || enteredOtp.isEmpty()) {
//                enteredOtp = ParamUtil.getString(actionRequest, "otp");
//                System.out.println("OTP from ParamUtil: '" + enteredOtp + "'");
//            }
//            
//            // Method 3: Try direct from action request
//            if (enteredOtp == null || enteredOtp.isEmpty()) {
//                enteredOtp = actionRequest.getParameter("otp");
//                System.out.println("OTP from actionRequest: '" + enteredOtp + "'");
//            }
//            
//            // Method 4: Try from HTTP request
//            if (enteredOtp == null || enteredOtp.isEmpty()) {
//                enteredOtp = httpRequest.getParameter("otp");
//                System.out.println("OTP from httpRequest: '" + enteredOtp + "'");
//            }
//            
//            // Method 5: Try all parameter names that might contain OTP
//            Enumeration<String> allParamNames = actionRequest.getParameterNames();
//            while (allParamNames.hasMoreElements() && (enteredOtp == null || enteredOtp.isEmpty())) {
//                String paramName = allParamNames.nextElement();
//                if (paramName.toLowerCase().contains("otp")) {
//                    enteredOtp = actionRequest.getParameter(paramName);
//                    System.out.println("Found OTP in parameter '" + paramName + "': '" + enteredOtp + "'");
//                }
//            }
//            
//            String sessionOtp = (String) session.getAttribute("pendingOTP");
//            Long generatedTime = (Long) session.getAttribute("otpGeneratedTime");
//            String email = (String) session.getAttribute("pendingUserEmail");
//            Long userId = (Long) session.getAttribute("pendingUserId");
//
//            System.out.println("=== FINAL OTP VALUES ===");
//            System.out.println("Entered OTP: '" + enteredOtp + "'");
//            System.out.println("Session OTP: '" + sessionOtp + "'");
//            System.out.println("Email: " + email);
//            System.out.println("User ID: " + userId);
//            System.out.println("Generated Time: " + generatedTime);
//
//            // Check if OTP is empty
//            if (enteredOtp == null || enteredOtp.trim().isEmpty()) {
//                String errorMsg = "Please enter the OTP code. No OTP parameter found in request.";
//                System.out.println("ERROR: " + errorMsg);
//                actionRequest.setAttribute("otpError", errorMsg);
//                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//                return false;
//            }
//            
//            enteredOtp = enteredOtp.trim();
//
//            // Check if session OTP exists
//            if (sessionOtp == null) {
//                String errorMsg = "OTP session expired. Please request a new OTP.";
//                System.out.println("ERROR: " + errorMsg);
//                actionRequest.setAttribute("otpError", errorMsg);
//                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//                return false;
//            }
//
//            // Check if OTP is expired
//            if (generatedTime == null || (System.currentTimeMillis() - generatedTime > 10 * 60 * 1000)) {
//                String errorMsg = "OTP has expired. Please request a new one.";
//                System.out.println("ERROR: " + errorMsg);
//                actionRequest.setAttribute("otpError", errorMsg);
//                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//                return false;
//            }
//
//            // Verify OTP
//            if (enteredOtp.equals(sessionOtp)) {
//                System.out.println("OTP VERIFICATION SUCCESSFUL");
//                
//                // Activate user
//                if (userId != null) {
//                    try {
//                        ServiceContext serviceContext = new ServiceContext();
//                        User user = UserLocalServiceUtil.getUserById(userId);
//                        UserLocalServiceUtil.updateStatus(userId, 0, serviceContext); // active
//                        
//                        System.out.println("User activated successfully: " + email + " (ID: " + userId + ")");
//
//                        // Cleanup session
//                        session.removeAttribute("pendingOTP");
//                        session.removeAttribute("otpGeneratedTime");
//                        session.removeAttribute("pendingUserEmail");
//                        session.removeAttribute("pendingUserFirstName");
//                        session.removeAttribute("pendingUserId");
//
//                        actionResponse.setRenderParameter("mvcPath", "/success.jsp");
//                        return true;
//                    } catch (Exception e) {
//                        System.out.println("Error activating user: " + e.getMessage());
//                        actionRequest.setAttribute("otpError", "Error activating account. Please contact support.");
//                        actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//                        return false;
//                    }
//                } else {
//                    actionRequest.setAttribute("otpError", "User session expired. Please sign up again.");
//                    actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//                    return false;
//                }
//            } else {
//                String errorMsg = "Invalid OTP code. Expected: " + sessionOtp + ", Got: " + enteredOtp;
//                System.out.println("ERROR: " + errorMsg);
//                actionRequest.setAttribute("otpError", errorMsg);
//                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//                return false;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            actionRequest.setAttribute("otpError", "System error during OTP verification: " + e.getMessage());
//            actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
//            return false;
//        }
//    }
//}




package OnlineFIR.fortesting;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import org.osgi.service.component.annotations.Component;

import OnlineFIR.constants.OnlineFIRPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + OnlineFIRPortletKeys.ONLINEFIR,
        "mvc.command.name=/user/verifyOtp"
    },
    service = MVCActionCommand.class
)
public class VerifyOTPMVCActionCommand implements MVCActionCommand {

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) {
        try {
            System.out.println("=== OTP VERIFICATION STARTED ===");
            
            // Get the HTTP request and session
            HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(actionRequest);
            HttpSession session = httpRequest.getSession();
            
            // Try different ways to get the OTP parameter
            String enteredOtp = null;
            
            // Method 1: Try ParamUtil
            enteredOtp = ParamUtil.getString(actionRequest, "otp");
            System.out.println("OTP from ParamUtil: '" + enteredOtp + "'");
            
            // Method 2: Try direct from action request
            if (enteredOtp == null || enteredOtp.isEmpty()) {
                enteredOtp = actionRequest.getParameter("otp");
                System.out.println("OTP from actionRequest: '" + enteredOtp + "'");
            }
            
            // Method 3: Try from HTTP request
            if (enteredOtp == null || enteredOtp.isEmpty()) {
                enteredOtp = httpRequest.getParameter("otp");
                System.out.println("OTP from httpRequest: '" + enteredOtp + "'");
            }
            
            String sessionOtp = (String) session.getAttribute("pendingOTP");
            Long generatedTime = (Long) session.getAttribute("otpGeneratedTime");

            System.out.println("=== OTP VALUES ===");
            System.out.println("Entered OTP: '" + enteredOtp + "'");
            System.out.println("Session OTP: '" + sessionOtp + "'");
            System.out.println("Generated Time: " + generatedTime);

            // Check if OTP is empty
            if (enteredOtp == null || enteredOtp.trim().isEmpty()) {
                String errorMsg = "Please enter the OTP code.";
                System.out.println("ERROR: " + errorMsg);
                actionRequest.setAttribute("otpError", errorMsg);
                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
                return false;
            }
            
            enteredOtp = enteredOtp.trim();

            // Check if session OTP exists
            if (sessionOtp == null) {
                String errorMsg = "OTP session expired. Please request a new OTP.";
                System.out.println("ERROR: " + errorMsg);
                actionRequest.setAttribute("otpError", errorMsg);
                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
                return false;
            }

            // Check if OTP is expired
            if (generatedTime == null || (System.currentTimeMillis() - generatedTime > 10 * 60 * 1000)) {
                String errorMsg = "OTP has expired. Please request a new one.";
                System.out.println("ERROR: " + errorMsg);
                actionRequest.setAttribute("otpError", errorMsg);
                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
                return false;
            }

            // Verify OTP
            if (enteredOtp.equals(sessionOtp)) {
                System.out.println("OTP VERIFICATION SUCCESSFUL");
                
                // ONLY NOW CREATE THE USER IN DATABASE
                try {
                    // Get user data from session
                    String loginName = (String) session.getAttribute("pendingUserLoginName");
                    String firstName = (String) session.getAttribute("pendingUserFirstName");
                    String lastName = (String) session.getAttribute("pendingUserLastName");
                    String email = (String) session.getAttribute("pendingUserEmail");
                    String password = (String) session.getAttribute("pendingUserPassword");
                    String roleName = (String) session.getAttribute("pendingUserRole");
                    String phoneNo = (String) session.getAttribute("pendingUserPhoneNo");
                    Long companyId = (Long) session.getAttribute("pendingUserCompanyId");

                    System.out.println("Creating user in database: " + email);
                    
                    ServiceContext serviceContext = new ServiceContext();
                    serviceContext.setCompanyId(companyId);

                    // Create user in database
                    User newUser = UserLocalServiceUtil.addUser(
                        0L, // creatorUserId
                        companyId, 
                        false, // autoPassword
                        password, 
                        password, 
                        false, // autoScreenName
                        loginName, 
                        email, 
                        java.util.Locale.getDefault(),
                        firstName, 
                        "", // middleName
                        lastName, 
                        0L, // prefixListTypeId
                        0L, // suffixListTypeId
                        true, // male
                        1, // birthdayMonth
                        1, // birthdayDay
                        1990, // birthdayYear
                        "", // jobTitle
                        0, new long[0], // groupIds
                        new long[0], // organizationIds
                        new long[0], // roleIds
                        new long[0], // userGroupIds
                        false, // sendEmail
                        serviceContext
                    );

                    // Set custom field for phone number if needed
                    // You can use Expando attributes for custom fields
                    
                    // Assign role
                    try {
                        Role role = RoleLocalServiceUtil.getRole(companyId, roleName);
                        if (role != null) {
                            UserLocalServiceUtil.addRoleUser(role.getRoleId(), newUser);
                            System.out.println("Role assigned: " + roleName);
                        }
                    } catch (Exception e) {
                        System.out.println("Error assigning role: " + e.getMessage());
                    }

                    System.out.println("User created successfully in database: " + email + " (ID: " + newUser.getUserId() + ")");

                    // Cleanup session - remove all temporary data
                    session.removeAttribute("pendingOTP");
                    session.removeAttribute("otpGeneratedTime");
                    session.removeAttribute("pendingUserLoginName");
                    session.removeAttribute("pendingUserFirstName");
                    session.removeAttribute("pendingUserLastName");
                    session.removeAttribute("pendingUserEmail");
                    session.removeAttribute("pendingUserPassword");
                    session.removeAttribute("pendingUserRole");
                    session.removeAttribute("pendingUserPhoneNo");
                    session.removeAttribute("pendingUserCompanyId");

                    actionResponse.setRenderParameter("mvcPath", "/success.jsp");
                    return true;
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error creating user in database: " + e.getMessage());
                    actionRequest.setAttribute("otpError", "Error creating account. Please try again or contact support.");
                    actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
                    return false;
                }
            } else {
                String errorMsg = "Invalid OTP code. Please try again.";
                System.out.println("ERROR: " + errorMsg);
                actionRequest.setAttribute("otpError", errorMsg);
                actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            actionRequest.setAttribute("otpError", "System error during OTP verification: " + e.getMessage());
            actionResponse.setRenderParameter("mvcPath", "/otpVerify1.jsp");
            return false;
        }
    }
}