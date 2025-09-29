package OnlineFIR.citizen;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

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
        "mvc.command.name=/fir/modify"
    },
    service = MVCActionCommand.class
)
public class ModifyFIRMVCActionCommand implements MVCActionCommand {

    @Override
    public boolean processAction(ActionRequest request, ActionResponse response) {
        long firId = ParamUtil.getLong(request, "firId");
        String newDetails = ParamUtil.getString(request, "modificationDetails");

        FIRRR fir = null;
        try {
            fir = FIRRRLocalServiceUtil.getFIRRR(firId);
            if (fir != null) {
                fir.setModificationDetails(newDetails);
                fir.setModifiedDate(new Date());
                fir.setStatus("Modified");

                FIRRRLocalServiceUtil.updateFIRRR(fir);
            }
        } catch (Exception e) {
            e.printStackTrace(); // or log.error
        }
return true;
    }
}
