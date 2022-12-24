# *ExpaWeb BackEnd API Request Forms
<div>
<h1><u>Login</u></h1>
http://localhost:8080/
<b>login</b>
 <p>
<label>Request Format:</label>
<br>
{ 
<br>
    "username"= "user@mail.com",
    <br>
    "password"= "password"
<br>
}
</p>  

# UserEndpoint Class
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
"username": "username",
<br>
"password": "Password",
<br>
"firstName": "First Name",
<br>
"lastName": "Last Name",
<br>
"gender": "M - F"
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
    "roleName"= "ROLE_USER"
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
    "userId" = "User Id",
<br>
    "roleName"= "ROLE_USER"
<br>
}
</p>                 

</div>

# PostEndpoint Class
<div>
<h1><u>New Post</u></h1>
http://localhost:8080/api/post/new

<p>
<label>Request Format:</label>
<br>
{ 
<br>
    "title"= "Post Title"
<br>
    "metaTitle"= "Keywords For SEO Optimization"
<br>
    "tag": "Science,History",
<br>
    "content"= "Post Content"
<br>
}
</p>
</div>

# TagEndpoint Class
<div>
<h1><u>New Tag</u></h1>
http://localhost:8080/api/tag/new
<p>
<label>Request Format:</label>
<br>
{
<br>
    "title"= "Tag Name",
    "metaTitle"= "Keywords for SEO Optimization",
    "content"= "Tag Description"
<br>
}
</p>
</div>
