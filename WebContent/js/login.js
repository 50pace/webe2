
function login(){
	const loginURL = '/PorucivanjeHrane/rest/login';
	const body = {};
	body.korisnickoIme = $('#usernameInput').val();
	body.lozinka = $('#passwordInput').val();
	$.ajax({
		  type: "POST",
		  url: loginURL,
		  data: JSON.stringify(body),
		  contentType: 'application/json',
		  success: function(data , status){
			  window.location.href = '/PorucivanjeHrane'
		  },
		  error: function(data , status){
			document.getElementById('alert').style.visibility= 'visible';
		  },
		});
	
}