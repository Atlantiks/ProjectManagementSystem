<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <c:if test="${empty requestScope.errors}">
<div class="container">
    <h1>Set ${info.availableSkills[0].name} level for developer <span style="color: cyan">${info.developer.firstName} ${info.developer.lastName}</span></h1>
    <h2>Please pick right skill level in the list below</h2></br>

    <form action="/set-skill-level" method="post">
    <label>Skill level</label>
    <select id="skillId" name="skillId">
        <c:forEach var="skill" items="${requestScope.info.availableSkills}">
            <option value="${skill.id}">${skill.level}</option>
        </c:forEach>
    </select>

    <input type="hidden" id="developerId" name="developerId" value="${info.developer.id}">

    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
    </form>
    </c:if>



    <c:if test="${not empty requestScope.errors}">
        <div class="container" style="color: red">
            <p> ${requestScope.errors} </p>
        </div>
    </c:if>

</div>