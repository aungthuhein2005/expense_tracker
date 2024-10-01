<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<c:import url="common/header.jsp"></c:import>
</head>
<body>

	<div class="container-fluid">
		<!--Nav bar -->
		<c:import url="common/nav.jsp"></c:import>

		<div class="container w-100">
			<div class="row mt-3">
				<c:forEach var="expense" items="${expenses}">
					<div class="col-sm-6 col-md-4 col-lg-3 col-xl-2 col-xxl-2">
						<div class="card" >
							<img src="${expense.image}" class="card-img-top object-fit-cover" width="100%" height="150px" alt="...">
							<div class="card-body">
								<h5 class="card-title">${expense.name}</h5>
								<p class="card-text">Quantity: ${expense.qty}</p>
								<p class="card-text text-primary">Price: ${expense.price}</p>
								<p class="card-text">Sub total: ${expense.subTotal}</p>
								<c:url var="detailLink" value="expense">
									<c:param name="mode" value="DETAILS"/>
									<c:param name="expenseId" value="${expense.id }"/>
								</c:url>
								<a href="${detailLink }" class="btn btn-secondary">View</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<c:import url="common/footer.jsp"></c:import>
</body>
</html>
