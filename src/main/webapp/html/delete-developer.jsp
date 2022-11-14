<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Delete developer by Id</h1>
    <h2>Please fill the form bellow to delete developer providing his id</h2></br>

    <form action="/delete-developer" method="post">
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