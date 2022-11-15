<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Create new customer</h1>
    <h2>Please fill the form bellow to add new customer to the system</h2></br>

    <form action="/add-customer" method="post">
    <label>Name</label> <input type="text" name="name" id="name" required></input>
    <label>Surname</label> <input type="text" name="surname" id="surname" required></input></br></br>
    <label>Company (optional)</label> <input type="text" name="company" id="company"></input></br></br>
    <label>Address</label> <input type="text" name="address" id="address" size="50" required></input></br></br>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <p> ${requestScope.errors} </p>
        </div>
    </c:if>

</div>