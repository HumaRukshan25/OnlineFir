
// this  i have done for custom login


//package OnlineFIR.portlet;
//
//import com.liferay.portal.kernel.log.Log;
//import com.liferay.portal.kernel.log.LogFactoryUtil;
//import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
//import com.liferay.portal.kernel.servlet.SessionErrors;
//import com.liferay.portal.kernel.servlet.SessionMessages;
//import com.liferay.portal.kernel.util.ParamUtil;
//import com.liferay.portal.kernel.util.PortalUtil;
//
//import javax.portlet.ActionRequest;
//import javax.portlet.ActionResponse;
//import javax.servlet.http.HttpSession;
//
//import org.osgi.service.component.annotations.Component;
//
//import OnlineFIR.constants.OnlineFIRPortletKeys;
//
//@Component(
//    immediate = true,
//    property = {
//        "javax.portlet.name=" + OnlineFIRPortletKeys.ONLINEFIR,
//        "mvc.command.name=/otp/verify"
//    },
//    service = MVCActionCommand.class
//)
//public class VerifyOtpMVCActionCommand implements MVCActionCommand {
//
//    private static final Log log = LogFactoryUtil.getLog(VerifyOtpMVCActionCommand.class);
//
//    @Override
//    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) {
//
//        String enteredOtp = ParamUtil.getString(actionRequest, "otp");
//        HttpSession session = PortalUtil.getHttpServletRequest(actionRequest).getSession();
//        
//        // Get the OTP from session
//        String expectedOtp = (String) session.getAttribute("EXPECTED_OTP");
//        String role = (String) session.getAttribute("USER_ROLE");
//        
//
//        // Debug logging
//        log.debug("Session ID: " + session.getId());
//        log.debug("Expected OTP from session: " + expectedOtp);
//        log.debug("Entered OTP: " + enteredOtp);
//        log.debug("User role: " + role);
//
//        // Check if OTP exists
//        if (expectedOtp == null) {
//            SessionErrors.add(actionRequest, "otpMissing");
//            log.error("No OTP found in session");
//            actionResponse.setRenderParameter("mvcPath", "/otp.jsp");
//            return false;
//        }
//
//        // Check if OTP has expired (5 minutes)
//        Long otpTimestamp = (Long) session.getAttribute("OTP_TIMESTAMP");
//        boolean isOtpExpired = otpTimestamp != null && 
//                              (System.currentTimeMillis() - otpTimestamp) > 300000;
//        
//        if (isOtpExpired) {
//            SessionErrors.add(actionRequest, "otpExpired");
//            log.error("OTP has expired");
//            session.removeAttribute("EXPECTED_OTP");
//            session.removeAttribute("OTP_CHALLENGE");
//            session.removeAttribute("OTP_TIMESTAMP");
//            actionResponse.setRenderParameter("mvcPath", "/otp.jsp");
//            return false;
//        }
//
//        // Verify OTP
//        if (expectedOtp.trim().equals(enteredOtp.trim())) {
//            SessionMessages.add(actionRequest, "otpVerified");
//            log.info("OTP verified successfully for role: " + role);
//            
//            // Clean up session
//            session.removeAttribute("EXPECTED_OTP");
//            session.removeAttribute("OTP_CHALLENGE");
//            session.removeAttribute("OTP_TIMESTAMP");
//
//            // Redirect based on role
//            if ("admin".equals(role)) {
//                actionResponse.setRenderParameter("mvcPath", "/dashboard.jsp");
//            } else {
//                actionResponse.setRenderParameter("mvcPath", "/success.jsp");
//            }
//        } else {
//            SessionErrors.add(actionRequest, "otpInvalid");
//            log.error("OTP verification failed. Expected: " + expectedOtp + ", Got: " + enteredOtp);
//            
//            // Stay on OTP page
//            if ("admin".equals(role)) {
//                actionResponse.setRenderParameter("mvcPath", "/admin_success.jsp");
//            } else {
//                actionResponse.setRenderParameter("mvcPath", "/otp.jsp");
//            }
//        }
//
//        return false;
//    }
//}



// this is for default admin to verify otp   for user_ table    1st form  after login
//
package OnlineFIR.fortesting;

import OnlineFIR.constants.OnlineFIRPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + OnlineFIRPortletKeys.ONLINEFIR,
        "mvc.command.name=/otp/verify"
    },
    service = MVCActionCommand.class
)
public class VerifyOtpMVCActionCommand0 implements MVCActionCommand {

    private static final Log log = LogFactoryUtil.getLog(VerifyOtpMVCActionCommand0.class);

    @Override
    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
        try {
            // Get the original request to access the session correctly
            HttpServletRequest request = PortalUtil.getOriginalServletRequest(
                PortalUtil.getHttpServletRequest(actionRequest)
            );

            HttpSession session = request.getSession(false);

            if (session == null) {
                log.error("No session found for OTP verification");
                actionResponse.setRenderParameter("mvcPath", "/admin_error.jsp");
                return true;
            }

            String expectedOtp = (String) session.getAttribute("EXPECTED_OTP");
            String enteredOtp = ParamUtil.getString(actionRequest, "otp");

            log.info("Expected OTP: " + expectedOtp + ", Entered OTP: " + enteredOtp);

            if (expectedOtp != null && expectedOtp.equals(enteredOtp)) {
                log.info("OTP Verified Successfully");
                session.removeAttribute("EXPECTED_OTP");
                actionResponse.setRenderParameter("mvcPath", "/dashboard1.jsp");
            } else {
                log.info("OTP Verification Failed");
                actionResponse.setRenderParameter("mvcPath", "/admin_error.jsp");
            }

        } catch (Exception e) {
            log.error("Error verifying OTP", e);
            actionResponse.setRenderParameter("mvcPath", "/admin_error.jsp");
        }

        return true;
    }
}


