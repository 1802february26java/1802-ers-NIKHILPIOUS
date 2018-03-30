window.onload =()=>{
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");
	
	sendProfileReq();
	
}

function sendProfileReq() {
	let xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = ()=>{
		
		if(xhr.readyState=== XMLHttpRequest.DONE && xhr.status===200){
			let data= JSON.parse(xhr.responseText);
			console.log("ioioio");
			console.log(data);
			
			viewProfile(data);
		}
	};
	
	xhr.open("GET",`profile.do?`);
	xhr.send();
	
}

function viewProfile(data){
	

	document.getElementById("userid").innerHTML = '<h4>USERID: &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+'<b>'+`${data.id}`+'</b></h4>';;
	document.getElementById("uname").innerHTML = '<h4>USERNAME: &nbsp;&nbsp;&nbsp;'+'<b>'+`${data.username}`+'</b></h4>';
	document.getElementById("fullname").innerHTML = '<h4>NAME: &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;'+'<b>'+`${data.firstName}`+" "+ `${data.lastName}`+'</b></h4>';
	document.getElementById("email").innerHTML = '<h4>EMAIL: &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+'<b>'+ `${data.email}`+'</b></h4>';
	
}