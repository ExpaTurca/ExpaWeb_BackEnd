# <h1>ExpaWeb BackEnd API Request Forms</h1>

<div>

<div>
<h1><u># New User #</u></h1>
http://localhost:8080/api/user/create
    <h3><u>Request Body</u></h3>
{   
<br>
    "firstName": "First Name",
<br>
    "lastName": "Last Name", 
<br>
    "email": "your_mail@mail.com",
<br>
    "username": "username",
<br>
    "password": "Password"
<br>
}
</div>

<div>
<h1><u># New Post #</u></h1>
<h3>http://localhost:8080/api/post/create</h3>

<h2><u>Request Body</u></h2>
{
<br>
"title": "Post Title"
<br>
"metaTitle": "Keywords For SEO Optimization"
<br>
"content": "Post Content"
<br>
}
</div>
<div>
<h1><u># New Comment #</u></h1>
<h3>http://localhost:/api/comment/create</h3>
<h2><u>Request Body</u></h2>
{
<br>
    "postId": "Which Post you are replying?",
<br>
    "content": "Your Comment"
<br>
}
</div>
</div>
