# *ExpaWeb BackEnd API

<div>
<h1><u>Home</u></h1>
http://localhost:8080/
</div>

<div>
<h1><u>Login</u></h1>
http://localhost:8080/
<b>login</b>
 <p>
<label>Request Format:</label>
<br>
{ 
<br>
    username = user@mail.com,
    <br>
    password = password
<br>
}
</p>  

</div>

<div>
<h1><u>Logout</u></h1>
http://localhost:8080/login?logout
</div>

#*UserEndpoint Class
<div>
<h1><u>Profile</u></h1>
http://localhost:8080/api/user/{userID}

</div>

<div>
<h1><u>New User</u></h1>
http://localhost:8080/api/user/new
<p>
<label>Request Format:</label>
<br>  
<p>
{    
<br>
"email": "user@mail.com",
<br>
"password": "Password",
<br>
"firstName": "Your Name",
<br>
"lastName": "Your Family Name",
<br>
"gender": "M for Male, F for Female",
<br>
"imageUrl": "profile.jpg",
<br>
<"roleName": "Role_*"

}
</p>
</div>


<div>
<h1><u>New Role</u></h1>
http://localhost:8080/api/role/new
<p>
<label>Request Format:</label>
<br>
{ 
<br>
    roleName= ROLE_*
<br>
}
</p> 
</div>

<div>
<h1><u>Assign Role to User</u></h1>
<label>
    http://localhost:8080/api/role/adduser
</label>                   
<p>
<label>Request Format:</label>
<br>
{ 
<br>
    email = user@mail.com,
<br>
    roleName= ROLE_*
<br>
}
</p>                 

</div>


<div>
<h1><u>Get Post</u></h1>
http://localhost:8080/api/post/{postID}

</div>


#*PostEndpoint Class
<div>
<h1><u>Get User's Posts</u></h1>
http://localhost:8080/api/post/get/{userID}&pg={pageNumber}

</div>

 <div>
<h1><u>New Post</u></h1>
http://localhost:8080/api/post/new

<p>
<label>Request Format:</label>
<br>
{ 
<br>
    title = Post Title
<br>
    metaTitle = Keywords For SEO Optimization
<br>
    summary = Summary
<br>
    content = Post
<br>
}
</p>
</div>

<div>
<h1><u>Edit Post</u></h1>
http://localhost:8080/api/post/edit
</div>

<div>
<h1><u>Delete Post</u></h1>
http://localhost:8080/api/post/remove/{id}
</div>


#*TagEndpoint Class
<div>
<h1><u>Get Tag</u></h1>
http://localhost:8080/api/tag/{tagID}
</div>

<div>
<h1><u>New Tag</u></h1>
http://localhost:8080/api/tag/new
<p>
<label>Request Format:</label>
<br>
{
<br>
    title = Tag Name,
    metaTitle = Keyword for SEO Optimization,
    content = Tag Description
<br>
}
</p>
</div>

<div>
<h1><u>Edit Tag</u></h1>
http://localhost:8080/api/tag/edit
</div>

<div>
<h1><u>Delete Tag</u></h1>
http:localhost:8080/api/tag/delete
</div>
