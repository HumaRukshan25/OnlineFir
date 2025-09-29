package OnlineFIR.admin;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import OnlineFIR.constants.OnlineFIRPortletKeys;

@Component(
    immediate = true,
    property = {
        "javax.portlet.name=" + OnlineFIRPortletKeys.ONLINEFIR,
        "mvc.command.name=/admin/viewAllFIRs"
    },
    service = MVCRenderCommand.class
)
public class ViewAllFIRsRenderCommand implements MVCRenderCommand {
    @Override
    public String render(RenderRequest request, RenderResponse response) 
            throws PortletException {
        return "/viewAllFIRs.jsp";
    }
}
