window.onload = () => {
	
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");
	
    document.getElementById("submit").addEventListener("click",()=>{
//    let date = document.getElementById("dateldt").value;
    let amount= document.getElementById("amount").value;
    let status = document.getElementById("statusid").value;
    let type= document.getElementById("typeid").value;
    
	let xhr=new XMLHttpRequest();
	
	xhr.onreadystatechange = ()=>{
		if(xhr.readyState===XMLHttpRequest.DONE && xhr.status===200){ // this we retrive from response, as this moment we done with request
			let data= JSON.parse(xhr.responseText)
			console.log(data);
			sessionLogin(data);
			
		}
	};
	xhr.open("POST",`submit.do?amount=${amount}&statusid=${status}&typeid=${type}`);// imagine this as going to a particular servlet using the URI sumbit.do

	xhr.send();
});
}
    
function sessionLogin(data){
	if(data.message==="FAILED TO SUBMIT REQUEST"){
		document.getElementById("message").innerHTML = '<scope class="label label-danger label center"> Request wasnt able to submit ></scope>';
	}
	else 
		document.getElementById("message").innerHTML = '<scope class="label label-success label center"> Request successfully submitted ></scope>';	
}

function errorMessage(dataFromServer){
    document.getElementById("message").innerHTML = '<scope class="label label-danger label center"> Request not successfully submitted </scope>';
}