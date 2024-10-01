<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="expense">Expense Tracker.</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
					aria-controls="navbarSupportedContent" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link active"
							aria-current="page" href="expense">Home</a></li>
						<li class="nav-item"><a class="nav-link" href="expense?mode=FORM">Product</a></li>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
							role="button" data-bs-toggle="dropdown" aria-expanded="false">
								${sessionScope.user.email } </a>
							<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
								<li><a class="dropdown-item" href="login?mode=LOGOUT">Logout</a></li>
								<c:if test="${sessionScope.user.role eq 'admin' }">
									<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item" href="admin">Admin Dashboard</a></li>
								</c:if>
							</ul></li>
					</ul>
					<form class="d-flex" action="expense">
					<input class="form-control me-2" type="hidden"
							name="mode" value="SEARCH" >
						<input class="form-control me-2" type="search"
							placeholder="Search" name="query" aria-label="Search">
						<button class="btn btn-outline-success" type="submit">Search</button>
					</form>
				</div>
			</div>
		</nav>