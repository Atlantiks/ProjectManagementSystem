<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">
    <h1>Get project info by Id</h1>
    <p>Please type project id the filed bellow to get complete info about the project</p</br>

    <form action="/view-project-info" method="post">
    <label>Id</label> <input type="number" name="id" id="id" min="1" required></input>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <p> ${requestScope.errors} </p>
            </div>
        </c:if>

</div>