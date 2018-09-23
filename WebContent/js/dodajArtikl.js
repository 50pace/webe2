window.onload = function (){
	
		
	const restoraniURL = '/PorucivanjeHrane/rest/restaurants';
	$.ajax({
		type: 'GET',
		url : restoraniURL,
		success: function (data, status){
			const select = $('#restoranSelect');
			data.forEach(function(restoran, index, arr){
				const option = document.createElement('OPTION');
				option.value = restoran.id;
				option.innerHTML = restoran.naziv;
				select.append(option);
			})
			
			const url = window.location.pathname;
			if(url.includes("izmena")){
				$('#button').html('Izmeni artikl');
				const restoranId = url.substring(url.lastIndexOf('/') -1 , url.lastIndexOf('/'));
				const artiklId = url.substring(url.lastIndexOf('/') + 1);
				const restoranURL = '/PorucivanjeHrane/rest/restaurants/'+restoranId+'/articles/'+artiklId; 
				$.ajax({
					type:'GET',
					url:restoranURL,
					success:function(data,status){
						$('#naziv').val(data.naziv);
						$('#cijena').val(data.jedinicnaCena);
						$('#opis').val(data.opis);
						$('#kolicina').val(data.kolicina);
						$('#tip').val(data.tip);
						$('#restoranSelect').val(data.restoranId);
						$('#restoranSelect').prop("disabled",true);
					},
					error:function(error,status){
						console.log("greska");
					}
				});
			}
		}
	});
	
}

function dodajArtikl(){

	const body={};
	body.naziv=$('#naziv').val();
	body.jedinicnaCena=$('#cijena').val();
	body.opis=$('#opis').val();
	body.kolicina=$('#kolicina').val();
	body.tip=$('#tip').val();
	
	
	const restoranId=$('#restoranSelect').val();
	const artiklURL='/PorucivanjeHrane/rest/restaurants/'+restoranId+'/articles';
	
	const url = window.location.pathname;
	if(url.includes("izmena")){
		
		const id = url.substring(url.lastIndexOf('/') + 1);
		$.ajax({
			type:'PUT',
			url:artiklURL+'/'+id,
			contentType:'application/json',
			data:JSON.stringify(body),
			success:function(data,status){
				window.location.href='/PorucivanjeHrane/restorani/'+restoranId;
			},
			error:function(error,status){
				console.log("greska");
			}
		});
		
	}else{
		$.ajax({
			type:'POST',
			url:artiklURL,
			contentType:'application/json',
			data:JSON.stringify(body),
			success:function(data,status){
				window.location.href='/PorucivanjeHrane/restorani/'+restoranId;
			},
			error:function(error,status){
				console.log("greska");
			}
		});
	}
}