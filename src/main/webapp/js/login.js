window.onload=()=>{
	document.getElementById("login").addEventListener("click",()=>{ // everything below wil hjapend when you clolkc the login button
		let Username=document.getElementById("username").value;
		let Password= document.getElementById("password").value;
		
		let xhr=new XMLHttpRequest();
		
		xhr.onreadystatechange = ()=>{
			if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){ // this we retrive from response, as this moment we done with request

				let data= JSON.parse(xhr.responseText); //we parsting the json object we get from java
				console.log("somwethinf");
				
				sessionLogin(data);
				
			}
		};
		xhr.open("POST",`login.do?username=${Username}&password=${Password}`);
		xhr.send();
	});
	
}






function sessionLogin(data){
	if(data.message){
		document.getElementById("loginMessage").innerHTML='<span class="label label-danger label-center">Wrong credintials.</span>';
	}
	else{
		sessionStorage.setItem("empId",data.id);// it's reachable from java as well as javascript
		sessionStorage.setItem("empUsername",data.username);
		sessionStorage.setItem("empFirst",data.firstName);
		sessionStorage.setItem("empLast",data.lastName);
		sessionStorage.setItem("empEmail",data.email);
		sessionStorage.setItem("urId",data.employeeRole.id);
		var urId= sessionStorage.getItem("urId");
		
		if(urId==1){
		window.location.replace("home.do");
		}
		else if(urId==2){
			console.log(urId);
			window.location.replace("mngrHome.do");
		}
	}
}
