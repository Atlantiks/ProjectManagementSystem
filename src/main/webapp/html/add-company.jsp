<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Create new company</h1>
    <h2>Please fill the form bellow to add new company to the system</h2></br>

    <form action="/add-company" method="post">
    <label>Name</label> <input type="text" name="name" id="name" required></input></br></br>
    <label>Country</label> <input type="text" name="country" id="country" required></input></br></br>
    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
    </form>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <p> ${requestScope.errors} </p>
        </div>
    </c:if>

</div>