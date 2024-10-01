<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="../common/header.jsp"></c:import>
</head>
<body>

	<div class="container-fluid">

		<div class="container">
			<div class="card mt-5 col-10 col-sm-12 col-md-8 col-6  mx-auto">
				<div class="card-body">
				<c:if test="${not empty emailAlreadyExists and emailAlreadyExists }">
						<div class="alert alert-danger" role="alert">Your Email Already Exists</div>
					</c:if>
					<c:if test="${not empty ok}">
						<c:choose>
							<c:when test="${ok }">
								<div class="alert alert-success" role="alert">Your account is created</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-danger" role="alert">Account
							creation is failed</div>
							</c:otherwise>
						</c:choose>
					</c:if>
					<h5 class="card-title">Create New User</h5>
					<form action="user" method="post">
						<input type="hidden" name="mode" value="REGISTER" />
						<div class="mb-3">
							<label for="firstname" class="form-label">First Name </label> <input
								type="text" class="form-control" id="firstname" name="firstname">
						</div>
						<div class="mb-3">
							<label for="lastname" class="form-label">Last Name </label> <input
								type="text" class="form-control" id="lastname" name="lastname">
						</div>
						<div class="mb-3">
							<label for="email" class="form-label">Email </label> <input
								type="email" class="form-control" id="email" name="email">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">Password </label> <input
								type="password" class="form-control" id="password"
								name="password">
						</div>
						<button type="submit" class="btn btn-primary float-end">Register</button>
						<p class="card-text">Already have an account? <a href="login">Login here</a> </p>
					</form>
				</div>
			</div>
		</div>

	</div>
	<c:import url="../common/footer.jsp"></c:import>
</body>
</html>
