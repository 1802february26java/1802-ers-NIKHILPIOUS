window.onload =()=>{
	document.getElementById("username").innerHTML=  sessionStorage.getItem("customerUsername");
	
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
	
	let panel = document.getElementById("proileP");
	
	
		let h4a = document.createElement('h4');
		let h4b = document.createElement('h4');
		let h4c = document.createElement('h4');
		let h4d = document.createElement('h4');
		let h4e = document.createElement('h4');
		
		console.log( `${data.username}`);
		
		let htxt1 = document.createTextNode("UserName:"+ `${data.username}`);
		let htxt2 = document.createTextNode("Name: "+ `${data.firstName}`+" "+ `${data.lastName}`);
		let htxt3 = document.createTextNode("Email: "+`${data.email}` );
		
		console.log();
		h4a.appendChild(htxt1);
		h4b.appendChild(htxt2);
		h4c.appendChild(htxt3);
		
		
		panel.appendChild(h4a);
		panel.appendChild(h4b);
		panel.appendChild(h4c);
		
		
		
		
	
	
}