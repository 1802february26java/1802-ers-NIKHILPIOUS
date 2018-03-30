window.onload = () => {
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");
	
    document.getElementById("update").addEventListener("click",()=>{
    let empId= sessionStorage.getItem("empId");
    
    let firstname = document.getElementById("firstName").value;
    let lastname= document.getElementById("lastName").value;
    let email = document.getElementById("email").value;
    let password= document.getElementById("password").value;
    
    if(!firstname){
    	
    	firstname=sessionStorage.getItem("empFirst");
    }
    if(!lastname){
    	lastname=sessionStorage.getItem("empLast");
    }
    if(!email){
    	email=sessionStorage.getItem("empEmail");
    }
    
    
	let xhr=new XMLHttpRequest();
	
	xhr.onreadystatechange = ()=>{
		if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){
			let data= JSON.parse(xhr.responseText)
			sessionLogin(data);
			
		}
	};
	xhr.open("POST",`updateprof.do?empId=${empId}&firstname=${firstname}&lastname=${lastname}&email=${email}&password=${password}`);

	xhr.send();
});
}
    
function sessionLogin(data){
	if(data.message==="UPDATE FAILED"){
		document.getElementById("updatEmessage").innerHTML = '<scope class="label label-danger label center"> Profile update failed ></scope>';
	}
	else 
		document.getElementById("updatEmessage").innerHTML = '<scope class="label label-success label center"> Profile updated></scope>';	
}

