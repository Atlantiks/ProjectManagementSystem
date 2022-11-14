<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Get all developers for a project with Id</h1>
    <h2></h2>
    <h3>Please enter project id to find all developers involved in the project</h3></br>

    <form action="/find-all-developers-in-project" method="post">
    <label>Id</label> <input type="number" name="id" id="id" required></input>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <p> ${requestScope.errors} </p>
            </div>
        </c:if>

</div>