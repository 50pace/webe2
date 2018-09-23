window.onload = function (){
	
	const url = window.location.pathname;
	if(url.includes("izmena")){
		$('#button').html('Izmeni vozilo');
		var urlArray= url.split('/');
		
		const delivererUsername =urlArray[urlArray.length - 2];
		const carId = urlArray[urlArray.length - 1];
		const carURL = '/PorucivanjeHrane/rest/deliverers/'+delivererUsername+'/cars/'+carId ; 
		$.ajax({
			type:'GET',
			url:carURL,
			success:function(data,status){
				$('#marka').val(data.marka);
				$('#model').val(data.model);
				$('#regOznaka').val(data.registarskaOznaka);
				$('#napomena').val(data.napomena);
				$('#tipovi').val(data.tip);
				$('#upotreba').val(data.voziloUupotrebi);
				if(data.voziloUupotrebi){
					$('#upotreba').prop('checked',true);
				}
				$('#datum').val(data.godinaProizvodnje);
				$('#dostavljaci').val(delivererUsername);
			},
			error:function(error,status){
				console.log("greska");
			}
		});
	}
		
	const deliverersURL = '/PorucivanjeHrane/rest/deliverers';
	$.ajax({
		type: 'GET',
		url : deliverersURL,
		success: function (data, status){
			const select = $('#dostavljaci');
			data.forEach(function(dostavljac, index, arr){
				const option = document.createElement('OPTION');
				option.value = dostavljac.korisnickoIme;
				option.innerHTML = dostavljac.korisnickoIme;
				select.append(option);
			})
		}
	});
}

function dodajVozilo(){

	const body={};
	body.marka=$('#marka').val();
	body.model=$('#model').val();
	body.registarskaOznaka=$('#regOznaka').val();
	body.tip=$('#tipovi').val();
	body.godinaProizvodnje=$('#datum').val();
	body.voziloUupotrebi=document.getElementById('upotreba').checked;
	body.napomena=$('#napomena').val();
	
	const dostavljacIme=$('#dostavljaci').val();
	const voziloURL='/PorucivanjeHrane/rest/deliverers/' + dostavljacIme + '/cars'
	
	const url = window.location.pathname;
	if(url.includes("izmena")){
		
		const id = url.substring(url.lastIndexOf('/') + 1);
		$.ajax({
			type:'PUT',
			url:voziloURL+'/'+id,
			contentType:'application/json',
			data:JSON.stringify(body),
			success:function(data,status){
				window.location.href='/PorucivanjeHrane/dostavljaci/'+dostavljacIme;
			},
			error:function(error,status){
				console.log("greska");
			}
		});
		
	}else{
		$.ajax({
			type:'POST',
			url:voziloURL,
			contentType:'application/json',
			data:JSON.stringify(body),
			success:function(data,status){
				window.location.href='/PorucivanjeHrane/dostavljaci/'+dostavljacIme;
			},
			error:function(error,status){
				console.log("greska");
			}
		});
	}
}