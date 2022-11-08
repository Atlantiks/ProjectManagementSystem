<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="navigationBar.jsp" %>

<div class="container">
    <h1>Create new developer</h1>
    <h2>Please fill the form bellow to add new developer to database</h2></br>

    <form action="/add-developer" method="post">
    <label>Name</label> <input type="text" name="name" id="name" required></input>
    <label>Surname</label> <input type="text" name="surname" id="surname" required></input></br></br>
    <p>Please select sex of a new developer:</p>

    <input type="radio" id="male" name="sex" value="M">
    <label for="male">Male</label><br>
    <input type="radio" id="female" name="sex" value="F">
    <label for="female">Female</label></br></br>

    <button type="submit">Submit</button>
    </form>
</div>