
window.onload  = function (){
	const url = window.location.pathname;
	if(url.includes("izmena")){
		$('#button').html('Izmeni restoran');
		const id = url.substring(url.lastIndexOf('/') + 1);
		const restoranURL = '/PorucivanjeHrane/rest/restaurants/'+id 
		$.ajax({
			type:'GET',
			url:restoranURL,
			success:function(data,status){
				$('#naziv').val(data.naziv);
				$('#adresa').val(data.adresa);
				$('#kategorija').val(data.kategorija);
			},
			error:function(error,status){
				console.log("greska");
			}
		});
	}
}

function addRestaurant(){
	
	const restoranURL='/PorucivanjeHrane/rest/restaurants';
	const body={};
	body.naziv=$('#naziv').val();
	body.adresa=$('#adresa').val();
	body.kategorija=$('#kategorija').val();
	
	const url = window.location.pathname;
	if(url.includes("izmena")){
		
		const id = url.substring(url.lastIndexOf('/') + 1);
		$.ajax({
			type:'PUT',
			url:restoranURL+'/'+id,
			contentType:'application/json',
			data:JSON.stringify(body),
			success:function(data,status){
				window.location.href='/PorucivanjeHrane/restorani';
			},
			error:function(error,status){
				console.log("greska");
			}
		});
		
	}else{
		$.ajax({
			type:'POST',
			url:restoranURL,
			contentType:'application/json',
			data:JSON.stringify(body),
			success:function(data,status){
				window.location.href='/PorucivanjeHrane/restorani';
			},
			error:function(error,status){
				console.log("greska");
			}
		});
	}
}

