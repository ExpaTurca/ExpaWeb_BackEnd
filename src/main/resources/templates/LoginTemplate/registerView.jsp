<!doctype html>
<html xmlns:th="https://www.thymeleaf.org/">

<head>
    <title>Expa Web</title>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap" rel="stylesheet">
    <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

<body class="img js-fullheight" style="background-image: url(images/bg.jpg);">
<section class="ftco-section m-0">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 text-center mb-5">
                <h2 class="heading-section">Register Page</h2>
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5">
                <div class="login-wrap p-0">
                    <h3 class="mb-4 text-center">New User</h3>

                    <form th:action="@{/register}" th:object="${register}"  class="signin-form"  method="post">
                        <div class="form-group">
                            <input class="form-control" id="email" th:field="*{email}" placeholder="Email" required type="text">
                        </div>

                        <div class="form-group">
                            <input class="form-control" id="username" th:field="*{username}" placeholder="Username" required type="text">
                        </div>

                        <div class="form-group">
                            <input class="form-control" id="password" th:field="*{password}" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}" placeholder="Password" required type="password">
                            <span class="fa fa-fw fa-eye field-icon toggle-password" toggle="#password-field"></span>
                        </div>

                        <div class="form-group">
                            <input class="form-control" id="firstName" th:field="*{firstName}" placeholder="First Name"
                                   required type="text">
                        </div>

                        <div class="form-group">
                            <input class="form-control" id="lastName" th:field="*{lastName}" placeholder="Last Name"
                                   required type="text">
                        </div>

                        <div class="form-group">
                            <input class="form-control" id="dateofbirth" th:field="*{dateofbirth}" placeholder="BirthDate"
                                   required type="date">
                        </div>

                        <div class="form-group">
                            <select class="">
                                <option th:field="${gender}">Male</option>
                                <option th:field="${gender}">Woman</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <button class="form-control btn btn-primary submit px-3" type="submit">Sign Up</button>
                        </div>
                    </form>

                    <p class="w-100 text-center">&mdash; Or Sign Up With &mdash;</p>
                    <div class="social d-flex text-center">
                        <a class="px-2 py-2 mr-md-1 rounded" href="#"><span class="ion-logo-facebook mr-2"></span>
                            Facebook</a>
                        <a class="px-2 py-2 ml-md-1 rounded" href="#"><span class="ion-logo-twitter mr-2"></span>
                            Twitter</a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>

<script src="js/jquery.min.js"></script>
<script src="js/popper.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/main.js"></script>

</body>
</html>
