window.onload = () => {
    document.getElementById("update").addEventListener("click",()=>{
    let empId= sessionStorage.getItem("empId");
    
    let firstname = document.getElementById("firstName").value;
    let lastname= document.getElementById("lastName").value;
    let email = document.getElementById("email").value;
    let password= document.getElementById("password").value;
    
    if(!firstname){//means empty string
    	
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
		if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){ // this we retrive from response, as this moment we done with request
			//yoou are getting an 
			let data= JSON.parse(xhr.responseText)
			console.log(data);
			sessionLogin(data);
			
		}
	};
	xhr.open("POST",`updateprof.do?empId=${empId}&firstname=${firstname}&lastname=${lastname}&email=${email}&password=${password}`);// imagine this as going to a particular servlet using the URI sumbit.do

	xhr.send();
});
}
    
function sessionLogin(data){
	
//		sessionStorage.setItem("customerId",data.id);// it's reachable from java as well as javascript
//		sessionStorage.setItem("customerUsername",data.username);
		
		window.location.replace("success.do");
	
}

function errorMessage(dataFromServer){
    document.getElementById("message").innerHTML = '<scope class="label label-danger label center"> Request not successfully submitted ></scope>';
}