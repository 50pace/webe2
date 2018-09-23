
function register(){
	
	const registerURL ='/PorucivanjeHrane/rest/register';
	const body = {};
	body.korisnickoIme = $('#usernameInput').val();
	body.lozinka = $('#passwordInput').val();
	body.email = $('#emailInput').val();
	body.ime = $('#imeInput').val();
	body.prezime = $('#prezimeInput').val();
	body.telefon = $('#telefonInput').val();
	
	$.ajax({
		type:'POST',
		url:registerURL,
		contentType:'application/json',
		data: JSON.stringify(body),
		success: function(data, status){
			window.location.href = '/PorucivanjeHrane/login';
		},
		error: function(error, status){
			
		}
	});

	
}