function sendPostQuery(address, body) {
	let xhr = new XMLHttpRequest();
	xhr.open("POST", address, true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			if(xhr.responseText == "registered") {
				window.location.href = "/login";
			} else {
				alert(xhr.responseText);
			}
		} else {
		}
	};
	xhr.send(body);
}

function sendGetQuery(address, callback) {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", address);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-url');
	xhr.addEventListener("readystatechange", () => {
		if (xhr.readyState === 4 && xhr.status === 200) {
			console.log(xhr.responseText);
			callback(JSON.parse(xhr.responseText));
		} else {
		}
	});
 	xhr.send();
}

function sendRegister() {
	if(username.value != "" && email.value != "" && password.value != "") {
		sendPostQuery('/register', 'username=' + encodeURIComponent(username.value) + "&email=" + encodeURIComponent(email.value) + "&password=" + encodeURIComponent(password.value));
	}
	
}
