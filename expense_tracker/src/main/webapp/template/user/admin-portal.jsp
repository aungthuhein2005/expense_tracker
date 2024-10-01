<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<c:import url="../common/header.jsp" />
	<link rel="stylesheet" href="https://cdn.datatables.net/2.1.4/css/dataTables.bootstrap5.css
				https://cdn.datatables.net/buttons/3.1.1/css/buttons.bootstrap5.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://cdn.datatables.net/2.1.4/js/dataTables.js"></script>
	<script src="https://cdn.datatables.net/2.1.4/js/dataTables.bootstrap5.js"></script>
</head>
<body>
	<div class="container-fluid">
		<c:import url="../common/nav.jsp" />
		
		<table id="user" class="table table-striped" style="width:100%">
        <thead>
            <tr>
            <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Enable</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users }" var="user">
            <tr>
             <td>${user.id }</td>
                         <td>${user.firstName }</td>
                <td>${user.lastName }</td>
                <td>${user.email }</td>
                <td>${user.enable ? 'Yes' : 'No' }</td>
                <td>
                	<c:url var="enableLink" value="admin" >
                		<c:param name="mode" value="ENABLE"/>
                		<c:param name="userId" value="${user.id }"/>
                	</c:url>
                		<c:url var="disableLink" value="admin" >
                		<c:param name="mode" value="DISABLE"/>
                		<c:param name="userId" value="${user.id }"/>
                	</c:url>
                	<c:if test="${user.enable }">
                		<a href="${disableLink }" class="btn btn-danger">Disable</a>
                	</c:if>	
                	<c:if test="${not user.enable }">
                		<a href="${enableLink }" class="btn btn-success">Enable</a>
                	</c:if>	
                </td>
            </tr>
            
            </c:forEach>
        </tbody>
    </table>
	</div>
	
	<c:import url="../common/footer.jsp" />
	<script type="text/javascript">
		new DataTable('#user');
	</script>
</body>
</html>