<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">

<c:if test="${empty requestScope.customer}">
    <h1>Update customer by Id</h1>
    <h2>Please fill the form bellow to find customer you wish to update providing his id</h2></br>

    <form action="/update-customer" method="get">
    <label>Id</label> <input type="number" name="id" id="id" min="1" required></input>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>
</c:if>

<c:if test="${not empty requestScope.customer}">
    <h1>Update existing customer</h1>
    <h2>Please fill the form bellow to update existing customer in the system</h2></br>
    <form action="/update-customer" method="post">
        <input type="hidden" id="projectId" name="id" value="${requestScope.customer.id}">
        <label>Name</label> <input type="text" name="name" id="name" value="${requestScope.customer.name}" required></input></br></br>
        <label>Surname</label> <input type="text" name="surname" id="description" value="${requestScope.customer.surname}" required></input></br></br>
        <label>Company</label> <input type="text" name="company" id="company" value="${requestScope.customer.company}"></input></br></br>
        <label>Address</label> <input type="text" name="address" id="address" value="${requestScope.customer.address}" size="60" required></input></br></br>

        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </form>
</c:if>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <p> ${requestScope.errors} </p>
            </div>
        </c:if>

</div>