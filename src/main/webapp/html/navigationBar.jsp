<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Project Management System</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Services</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
      <ul class="navbar-nav">
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Developer</a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" href="/add-developer">Create</a></li>
                  <li><a class="dropdown-item" href="/find-developer">Find</a></li>
                  <li><a class="dropdown-item" href="/update-developer">Update</a></li>
                  <li><a class="dropdown-item" href="/delete-developer">Delete</a></li>
                  <li><a class="dropdown-item" href="/find-all-devs">View All</a></li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Project</a>
                              <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="/add-project">Create</a></li>
                                <li><a class="dropdown-item" href="/find-project">Find</a></li>
                                <li><a class="dropdown-item" href="/update-project">Update</a></li>
                                <li><a class="dropdown-item" href="/delete-project">Delete</a></li>
                                <li><a class="dropdown-item" href="/find-all-projects">View All</a></li>
                                <li><a class="dropdown-item" href="/find-all-developers-in-project">View developers</a></li>
                                <li><a class="dropdown-item" href="/get-project-cost">Get project cost</a></li>
                                <li><a class="dropdown-item" href="/view-projects-info">View projects info</a></li>
                              </ul>
                            </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Company</a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" href="/add-company">Create</a></li>
                  <li><a class="dropdown-item" href="/find-company">Find</a></li>
                  <li><a class="dropdown-item" href="/update-company">Update</a></li>
                  <li><a class="dropdown-item" href="/delete-company">Delete</a></li>
                  <li><a class="dropdown-item" href="/view-all-companies">View All</a></li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Customer</a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" href="/add-customer">Create</a></li>
                  <li><a class="dropdown-item" href="/find-customer">Find</a></li>
                  <li><a class="dropdown-item" href="/update-customer">Update</a></li>
                  <li><a class="dropdown-item" href="/delete-customer">Delete</a></li>
                  <li><a class="dropdown-item" href="/view-all-customers">View All</a></li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Skill</a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" href="/add-skill">Create</a></li>
                  <li><a class="dropdown-item" href="/assign-skill">Assign</a></li>
                  <li><a class="dropdown-item" href="/delete-skill">Delete</a></li>
                  <li><a class="dropdown-item" href="/view-all-skills">View All</a></li>
                </ul>
              </li>
      </ul>
    </div>
  </div>
</nav>

</body>
</html>