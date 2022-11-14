<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">
    <h1>Find project by Id</h1>
    <h2>Please fill the form bellow to find project providing its id</h2></br>

    <form action="/find-project" method="post">
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