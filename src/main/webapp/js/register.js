window.onload = () => {
	document.getElementById("username").innerHTML = sessionStorage.getItem("empUsername");
	function myFunction() {
		var x = document.getElementById("myText").required;
		document.getElementById("demo").innerHTML = x;
	}

	document.getElementById("submit").addEventListener("click", () => {

		let password = document.getElementById("password").value;
		let repeatPassword = document.getElementById("repeatPassword").value;
		if(password !== repeatPassword) {
			document.getElementById("registrationMessage").innerHTML = '<span class="label label-danger label-center">Password mismatch.</span>';
			return;
		}


		document.getElementById("firstName").required;
		let firstname = document.getElementById("firstName").value;
		let lastname = document.getElementById("lastName").value;
		let userName = document.getElementById("newusername").value;
		let email = document.getElementById("email").value;
		let roleid = document.getElementById("roleId").value;


		let xhr = new XMLHttpRequest();

		xhr.onreadystatechange = () => {

			if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

				let data = JSON.parse(xhr.responseText);
				console.log(data);

				register(data);
			}
		};
		


		xhr.open("POST",`register.do?firstname=${firstname}&lastname=${lastname}&username=${userName}&password=${password}&email=${email}&ur_id=${roleid}`);

		xhr.send();
	});
}

function disableAllComponents() {
	document.getElementById("firstName").setAttribute("disabled","disabled");
	document.getElementById("lastName").setAttribute("disabled","disabled");
	document.getElementById("username").setAttribute("disabled","disabled");
	document.getElementById("password").setAttribute("disabled","disabled");
	document.getElementById("repeatPassword").setAttribute("disabled","disabled");
	document.getElementById("email").setAttribute("disabled","disabled");
	document.getElementById("roleId").setAttribute("disabled","disabled");
	document.getElementById("submit").setAttribute("disabled","disabled");
}

function register(data) {
	if(data.message==="REGISTRATION SUCCESSFUL"){
		disableAllComponents();
		document.getElementById("registrationMessage").innerHTML = '<span class="label label-success label-center">Registration successful.</span>';
		setTimeout(() => { window.location.replace("mngrHome.do"); }, 3000);
	}
	else if(data.message==="REGISTRATION UNSUCCESSFUL"){
		document.getElementById("registrationMessage").innerHTML = '<span class="label label-warning label-center">Something wrong!Make sure no empty fileds.</span>';
	}
	else if(data.message==="USERNAME EXIST"){
		document.getElementById("registrationMessage").innerHTML = '<span class="label label-danger label-center">User name exists</span>';
	}
	else{
		whindow.location.replace("unauthorized.html");
	}

}