<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">

<c:if test="${empty requestScope.project}">
    <h1>Update project by Id</h1>
    <h2>Please fill the form bellow to find project you wish to update providing its id</h2></br>

    <form action="/update-project" method="get">
    <label>Id</label> <input type="number" name="id" id="id" min="1" required></input>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>
</c:if>

<c:if test="${not empty requestScope.project}">
    <h1>Update existing <span style="color: blue">project</span></h1>
    <h2>Please fill the form bellow to update existing project in the system</h2></br>
    <form action="/update-project" method="post">
        <input type="hidden" id="projectId" name="id" value="${requestScope.project.id}">
        <label>Name</label> <input type="text" name="name" id="name" value="${requestScope.project.name}" required></input></br></br>
        <label>Description</label> <input type="text" name="description" id="description" value="${requestScope.project.description}" size="80" required></input></br></br>

        <label>Date</label> <input type="date" name="date" id="date" value="${requestScope.project.date}" required></input></br></br>

    <select id="status" name="status">
        <option value="${requestScope.project.status}">${requestScope.project.status}</option>

        <c:if test="${requestScope.project.status ne 'Active'}">
            <option value="Active">Active</option>
        </c:if>
        <c:if test="${requestScope.project.status ne 'Inactive'}">
            <option value="Inactive">Inactive</option>
        </c:if>
        <c:if test="${requestScope.project.status ne 'Discontinued'}">
            <option value="Discontinued">Discontinued</option>
        </c:if>
        <c:if test="${requestScope.project.status ne 'Not commissioned'}">
            <option value="Not commissioned">Not commissioned</option>
        </c:if>
    </select>

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