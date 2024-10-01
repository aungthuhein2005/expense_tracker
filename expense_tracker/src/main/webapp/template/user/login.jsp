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
					<c:if test="${not empty ok and not ok}">
						<div class="alert alert-danger" role="alert">User Name or Password is incorrect</div>
					</c:if>
					<h5 class="card-title">Login here</h5>
					<form action="login" method="post">
						<input type="hidden" name="mode" value="LOGIN" />
						<div class="mb-3">
							<label for="email" class="form-label">Email </label> <input
								type="email" class="form-control" id="email" name="email">
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">Password </label> <input
								type="password" class="form-control" id="password"
								name="password">
						</div>
						<button type="submit" class="btn btn-primary float-end">Login</button>
						<p class="card-text">Don't have an account? <a href="user">Create here</a> </p>
						
					</form>
				</div>
			</div>
		</div>

	</div>
	<c:import url="../common/footer.jsp"></c:import>
</body>
</html>
