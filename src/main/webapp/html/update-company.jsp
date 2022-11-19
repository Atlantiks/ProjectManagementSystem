<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">

<c:if test="${empty requestScope.company}">
    <h1>Update company by Id</h1>
    <h2>Please fill the form bellow to find company you wish to update providing her id</h2></br>

    <form action="/update-company" method="get">
    <label>Id</label> <input type="number" name="id" id="id" min="1" required></input>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>
</c:if>

<c:if test="${not empty requestScope.company}">
    <h1>Update existing <span style="color: orange">project</span></h1>
    <h2>Please fill the form bellow to update existing project in the system</h2></br>
    <form action="/update-company" method="post">
        <input type="hidden" id="companyId" name="id" value="${requestScope.company.id}">

        <label>Name</label> <input type="text" name="name" id="name" value="${requestScope.company.name}" required></input></br></br>
        <label>Country</label> <input type="text" name="country" id="country" value="${requestScope.company.country}" required></input></br></br>

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