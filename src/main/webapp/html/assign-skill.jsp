<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Assign skill</h1>
    <h2>Please fill the form bellow to assign new skill to a particular developer</h2></br>

    <form action="/assign-skill" method="post">

        <label>Developer</label>
        <select id="developer" name="developer">
            <option value=""></option>
            <c:forEach var="developer" items="${requestScope.developers}">
                <option value="${developer.fullName}">${developer.fullName}</option>
            </c:forEach>
        </select>
        <label>Skill</label>
        <select id="skill" name="skill">
            <option value=""></option>
            <c:forEach var="skill" items="${requestScope.skills}">
                <option value="${skill}">${skill}</option>
            </c:forEach>
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