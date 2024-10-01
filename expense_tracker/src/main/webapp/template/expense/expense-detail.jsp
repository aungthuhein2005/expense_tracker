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
		<!--Nav bar -->
		<c:import url="../common/nav.jsp"></c:import>

		<div class="container w-100">
					<div class="col-lg-6 mt-5 mx-auto">
						<div class="card mb-5" >
							<img src="${expense.image}" class="card-img-top object-fit-cover" alt="...">
							<div class="card-body">
								<h5 class="card-title">${expense.name}</h5>
								<p class="card-text">Quantity: ${expense.qty}</p>
								<p class="card-text text-primary">Price: ${expense.price}</p>
								<p class="card-text">Sub total: ${expense.subTotal}</p>
								<p class="card-text">Description : ${expense.description }</p>
								<c:url var="updateLink" value="expense">
									<c:param name="mode" value="LOAD"></c:param>
									<c:param name="expenseId" value="${expense.id }"></c:param>
								</c:url>
								<c:url var="deleteLink" value="expense">
									<c:param name="mode" value="DELETE"></c:param>
									<c:param name="expenseId" value="${expense.id }"></c:param>
								</c:url>
								<a href="${updateLink }" class="btn btn-primary">Edit</a>
								<a href="${deleteLink }" class="btn btn-danger">Delete</a>
							</div>
						</div>
					</div>
		</div>
	</div>

	<c:import url="../common/footer.jsp"></c:import>
</body>
</html>
