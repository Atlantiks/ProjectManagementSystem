<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Create new <span style="color: blue">project</span></h1>
    <h2>Please fill the form bellow to add new project to the system</h2></br>

    <form action="/add-project" method="post">
    <label>Name</label> <input type="text" name="name" id="name" required></input></br></br>
    <label>Description</label> <input type="text" name="description" id="description" required></input></br></br>
    <label>Date</label> <input type="date" name="date" id="date" required></input></br></br>
    <p>Please select status of a new project:</p>

    <select id="status" name="status">
        <option value="Active">Active</option>
        <option value="Inactive">Inactive</option>
        <option value="Discontinued">Discontinued</option>
        <option value="Not commissioned">Not commissioned</option>
    </select>

      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <p> ${requestScope.errors} </p>
        </div>
    </c:if>

</div>