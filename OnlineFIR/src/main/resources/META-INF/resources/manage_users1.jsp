<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.model.User" %>
<%@ include file="/init.jsp" %>

<h2>Manage Users</h2>



<style>
    h2 {
        font-weight: 600;
        margin-bottom: 20px;
        color: #2c3e50;
    }

    /* Scrollable container */
    .table-container {
        max-width: 100%;
        overflow-x: auto;
    }

    .table {
        min-width: 900px; /* force proper width */
        border-collapse: collapse;
        background: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        border-radius: 8px;
        overflow: hidden;
        font-size: 13px; /* smaller font */
    }

    .table thead {
        background: #007bff;
        color: white;
        text-align: center;
    }

    .table thead th {
        padding: 8px;
        font-size: 13px;
        white-space: nowrap;
    }

    .table tbody tr:hover {
        background: #f2f9ff;
    }

    .table tbody td {
        padding: 6px 8px;
        text-align: center;
        color: #333;
        white-space: nowrap; /* prevent wrapping */
    }

    .btn {
        padding: 4px 8px;
        border-radius: 5px;
        font-size: 12px;
    }

    .btn-primary {
        background: #007bff;
        border: none;
    }

    .btn-primary:hover {
        background: #0056b3;
    }

    .btn-danger {
        background: #dc3545;
        border: none;
    }

    .btn-danger:hover {
        background: #a71d2a;
    }

    .aui-button {
        margin-right: 10px;
    }
</style>

<aui:button-row>
    <!-- Add User -->
    <portlet:renderURL var="addUserURL1">
        <portlet:param name="mvcRenderCommandName" value="/admin/addUser1" />
    </portlet:renderURL>
    <aui:button type="button" value="Add User" onClick="location.href='${addUserURL1}'" />

    <!-- Back to Dashboard -->
    <portlet:renderURL var="adminDashboardURL1">
        <portlet:param name="mvcRenderCommandName" value="/dashboard1" />
    </portlet:renderURL>
    <aui:button type="button" value="Back to Dashboard" onClick="location.href='${adminDashboardURL1}'" />
</aui:button-row>


<div class="table-container">
<table class="table table-bordered table-striped">
    <thead>
        <tr>
            <th>User ID</th>
            <th>Screen Name</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Login Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.userId}</td>
                <td>${user.screenName}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.emailAddress}</td>
                <td>${user.loginDate}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.status == 0}">Inactive</c:when>
                        <c:otherwise>Active</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <!-- Edit User 
                    <portlet:renderURL var="editUserURL1">
                        <portlet:param name="mvcRenderCommandName" value="/admin/editUser1" />
                        <portlet:param name="userId" value="${user.userId}" />
                    </portlet:renderURL>
                    <a href="${editUserURL1}" class="btn btn-primary">Edit</a>
                    -->

                    <!-- Delete User -->
                    <portlet:actionURL name="/admin/deleteUser1" var="deleteUserURL1">
                        <portlet:param name="userId" value="${user.userId}" />
                    </portlet:actionURL>
                    <aui:button type="button" value="Delete"
                        onClick="if(confirm('Are you sure?')) window.location.href='${deleteUserURL1}'" />
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>