function sendPostQuery(address, body) {
	let xhr = new XMLHttpRequest();
	xhr.open("POST", address, true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			console.log(xhr.responseText);
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
			callback(JSON.parse(xhr.responseText));
		} else {
		}
	});
 	xhr.send();
}

selectId = testCategory;
getCategories();
function getCategories() {
	sendGetQuery("/test/categories", updateSelection);
}

function updateSelection(array) {
	selectId.innerHTML = "";
	for(let i = 0; i < array.length; i++) {
		selectId.options[selectId.options.length] = new Option(array[i].name, array[i].id);
	}
}