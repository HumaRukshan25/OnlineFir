<%@ include file="/init.jsp" %>

<h1>Police Dashboard</h1>

<ul>
    <li>
        <portlet:renderURL var="viewAssignedFIRsURL">
            <portlet:param name="mvcRenderCommandName" value="/police/viewAssignedFIRs" />
        </portlet:renderURL>
        <a href="${viewAssignedFIRsURL}">View Assigned FIRs</a>
    </li>

    <li>
        <portlet:renderURL var="updateFIRURL">
            <portlet:param name="mvcRenderCommandName" value="/police/updateFIR" />
        </portlet:renderURL>
        <a href="${updateFIRURL}">Update FIR Progress</a>
    </li>

    <li>
        <portlet:renderURL var="closeFIRURL">
            <portlet:param name="mvcRenderCommandName" value="/police/closeFIR" />
        </portlet:renderURL>
        <a href="${closeFIRURL}">Close FIRs</a>
    </li>

    <li>
        <portlet:renderURL var="logoutURL">
            <portlet:param name="mvcRenderCommandName" value="/police/logout" />
        </portlet:renderURL>
        <a href="${logoutURL}">Logout</a>
    </li>
</ul>

