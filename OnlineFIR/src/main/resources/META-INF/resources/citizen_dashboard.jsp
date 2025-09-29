<%@ include file="/init.jsp" %>

<h1>Citizen Dashboard</h1>

<ul>
    <li>
        <portlet:renderURL var="fileFIRURL">
            <portlet:param name="mvcRenderCommandName" value="/citizen/fileFIR" />
        </portlet:renderURL>
        <a href="${fileFIRURL}">File New FIR</a>
    </li>

    <li>
        <portlet:renderURL var="trackFIRURL">
            <portlet:param name="mvcRenderCommandName" value="/citizen/trackFIR" />
        </portlet:renderURL>
        <a href="${trackFIRURL}">Track FIR Status</a>
    </li>

    <li>
        <portlet:renderURL var="modifyFIRURL">
            <portlet:param name="mvcRenderCommandName" value="/citizen/modifyFIR" />
        </portlet:renderURL>
        <a href="${modifyFIRURL}">Modify/Withdraw FIR</a>
    </li>

    <li>
        <portlet:renderURL var="logoutURL">
            <portlet:param name="mvcRenderCommandName" value="/citizen/logout" />
        </portlet:renderURL>
        <a href="${logoutURL}">Logout</a>
    </li>
</ul>
