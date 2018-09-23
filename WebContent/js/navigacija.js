function logout(){
	
	const logoutURL = '/PorucivanjeHrane/rest/logout';
	
	$.ajax({
		type : 'POST',
		url : logoutURL,
		success : function(data,status){
			window.location.href ='/PorucivanjeHrane/login';
		}
	});
	
}