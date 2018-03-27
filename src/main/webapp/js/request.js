window.onload = () => {
    document.getElementById("submit").addEventListener("click",()=>{
    let date = document.getElementById("dateldt").value;
    let amount= document.getElementById("amount").value;
    let status = document.getElementById("statusid").value;
    let type= document.getElementById("typeid").value;
    
	let xhr=new XMLHttpRequest();
	
	xhr.onreadystatechange = ()=>{
		if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){ // this we retrive from response, as this moment we done with request
			//yoou are getting an 
			let data= JSON.parse(xhr.responseText)
			console.log(data);
			sessionLogin(data);
			
		}
	};
	xhr.open("POST",`submit.do?date=${date}&amount=${amount}&statusid=${status}&typeid=${type}`);// imagine this as going to a particular servlet using the URI sumbit.do

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