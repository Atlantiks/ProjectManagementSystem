<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h1>Create new <span style="color: orange">developer</span></h1>
    <h2>Please fill the form bellow to add new developer to the system</h2></br>

    <form action="/add-developer" method="post">
    <label>Name</label> <input type="text" name="name" id="name" required></input>
    <label>Surname</label> <input type="text" name="surname" id="surname" required></input></br></br>
    <p>Please select sex of a new developer:</p>

    <input type="radio" id="male" name="sex" value="M" required>
    <label for="male">Male</label><br>
    <input type="radio" id="female" name="sex" value="F">
    <label for="female">Female</label></br></br>

    <label>Company</label>
    <select id="company" name="company">
        <option value="">none</option>
        <c:forEach var="company" items="${requestScope.companies}">
            <option value="${company.id}">${company.name}</option>
        </c:forEach>
    </select>

    <label>Salary</label> <input type="number" name="salary" id="salary" required></input></br></br>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>

    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <p> ${requestScope.errors} </p>
        </div>
    </c:if>

</div>