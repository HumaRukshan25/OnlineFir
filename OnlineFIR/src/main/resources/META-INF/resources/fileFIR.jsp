<%@ include file="/init.jsp" %>

<h2>File New FIR</h2>

<portlet:actionURL name="/fir/add" var="fileFIRActionURL" />

<aui:form action="<%= fileFIRActionURL %>" method="post" name="fileFIRForm">
    <aui:input name="complainantName" label="Your Name" required="true" />
    <aui:input name="incidentDate" label="Date of Incident" type="date" required="true" />
    <aui:input name="incidentDetails" label="Incident Details" type="textarea" required="true" />
    <aui:input name="userId" type="hidden" value="<%= themeDisplay.getUserId() %>" />
    <aui:button type="submit" value="Submit FIR" />
</aui:form>
