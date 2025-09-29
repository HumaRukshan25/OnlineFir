package OnlineFIR.citizen;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

import OnlineFIR.constants.OnlineFIRPortletKeys;
import fironlineser.model.FIRRR;
import fironlineser.service.FIRRRLocalServiceUtil;

import java.util.Date;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + OnlineFIRPortletKeys.ONLINEFIR,
        "mvc.command.name=/fir/add"
    },
    service = MVCActionCommand.class
)
public class FileFIRMVCActionCommand implements MVCActionCommand {

    @Override
    public boolean processAction(ActionRequest request, ActionResponse response) {
        String complainantName = ParamUtil.getString(request, "complainantName");
        String incidentDetails = ParamUtil.getString(request, "incidentDetails");
        Date incidentDate = ParamUtil.getDate(request, "incidentDate", new java.text.SimpleDateFormat("yyyy-MM-dd"));
        long userId = ParamUtil.getLong(request, "userId");

        try {
            ServiceContext serviceContext = ServiceContextFactory.getInstance(FIRRR.class.getName(), request);

            long firId = CounterLocalServiceUtil.increment(FIRRR.class.getName());
            FIRRR fir = FIRRRLocalServiceUtil.createFIRRR(firId);

            fir.setComplainantName(complainantName);
            fir.setIncidentDetails(incidentDetails);
            fir.setIncidentDate(incidentDate);
            fir.setStatus("Pending");
            fir.setUserId(userId);
            fir.setCreateDate(new Date());
            fir.setModifiedDate(new Date());

            // save FIR
            FIRRRLocalServiceUtil.addFIRRR(fir);

        } catch (PortalException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
