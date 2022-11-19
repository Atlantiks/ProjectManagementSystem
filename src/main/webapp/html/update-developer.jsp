<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">

<c:if test="${empty requestScope.developer}">
    <h1>Update developer by Id</h1>
    <h2>Please fill the form bellow to find developer you wish to update providing his id</h2></br>

    <form action="/update-developer" method="get">
    <label>Id</label> <input type="number" name="id" id="id" min="1" required></input>
      <input type="submit" value="Submit">
      <input type="reset" value="Reset">
    </form>
</c:if>

            <c:if test="${not empty requestScope.developer}">
                <h1>Update existing <span style="color: orange">developer</span></h1>
                <h2>Please fill the form bellow to update existing developer in the system</h2></br>
                <form action="/update-developer" method="post">
                 <input type="hidden" id="developerId" name="id" value="${requestScope.developer.id}">
                <label>Name</label> <input type="text" name="name" id="name" value="${requestScope.developer.name}" required></input>
                <label>Surname</label> <input type="text" name="surname" id="surname" value="${requestScope.developer.surname}"required></input></br></br>
                <p>Please update sex of the developer:</p>
                        <input type="radio" id="male" name="sex" value="M"
                        <c:if test="${requestScope.developer.sex eq 'M'}">checked</c:if>
                        required>
                        <label for="male">Male</label><br>
                        <input type="radio" id="female" name="sex" value="F"
                        <c:if test="${requestScope.developer.sex eq 'F'}">checked</c:if>
                        >
                        <label for="female">Female</label></br></br>

                    <label>Company</label>
                    <select id="company" name="company">
                        <c:if test="${requestScope.developer.companyName eq 'none'}">
                            <option value="">none</option>
                        </c:if>

                                                <c:forEach var="company" items="${requestScope.companies}">
                                                <c:if test="${requestScope.developer.companyName eq company.name}">
                                                    <option value="${company.name}">${company.name}</option>
                                                    </c:if>
                                                </c:forEach>

                        <c:if test="${requestScope.developer.companyName ne 'none'}">
                            <option value="">none</option>
                        </c:if>

                        <c:forEach var="company" items="${requestScope.companies}">
                        <c:if test="${requestScope.developer.companyName ne company.name}">
                            <option value="${company.name}">${company.name}</option>
                        </c:if>
                        </c:forEach>
                    </select> </br></br>
                    <label>Salary</label> <input type="number" name="salary" id="salary" min="0" size="6" value="${requestScope.developer.salary}" required></input>$</br></br>

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