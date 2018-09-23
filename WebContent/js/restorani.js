var restorani = [];

window.onload = function (){
	
	const restoraniURL = '/PorucivanjeHrane/rest/restaurants';
	
	$.ajax({
		type:'GET',
		url : restoraniURL,
		success: function(data, status){
			const list = $('#list');
			restorani = data;
			data.forEach(function (restoran, index, arr){
				generateElement(list, restoran);
			});
			const url = window.location.href
			if(url.includes('?')){
				const urlSplit = url.split('=');
				const tipRestorana = urlSplit[urlSplit.length - 1];
				$('#kategorijaPretraga').val(tipRestorana);
				pretraziRestorane();
			}
		}
	});
	
	
	
}

function generateElement(list,restoran){
	const uloga = $('#uloga').val();
	let element = '<div class="card" style="width: 18rem;">'+
	  '<img class="card-img-top" src="http://maderarestoran.com/images/gallery/large/Restoran-Madera-basta-nocu-detalj.jpg">'+
	  '<div class="card-body">'+
	    '<h5 class="card-title"><b>'+restoran.naziv+ '</b></h5>'+
	    '<p class="card-text">Adresa: '+restoran.adresa+'</p>'+
	    '<p class="card-text">Kategorija: '+restoran.kategorija.replace('_', ' ')+'</p>'+
	    '<a href="/PorucivanjeHrane/restorani/'+ restoran.id+ '" class="btn btn-primary">Pogledaj restoran</a>';
	
	if(uloga == 'Administrator'){
		 element +='<br><br><button  class="btn btn-warning" onClick="izmeniRestoran('+ restoran.id +')">Izmeni</button>';
		 element +='&nbsp;<button  class="btn btn-danger" onClick="obrisiRestoran('+ restoran.id+')">Obrisi</button>';

	}else if(uloga == 'Kupac'){
		if(!restoran.omiljen)
			element +='<br><br><button  class="btn btn-success" onClick="dodajOmiljeni('+ restoran.id +')">Dodaj u omiljene restorane</button>';
		
	}
	
	
	    
	 element += '</div></div>';
	list.append(element);
	list.append(document.createElement('br'));
}

function obrisiRestoran(id){
	const restoraniURL='/PorucivanjeHrane/rest/restaurants/'+id;
	
	$.ajax({
		type:'DELETE',
		url :restoraniURL,
		success : function(data,status){
			window.location.reload();
		
		},
		error:function(error,status){
			console.log("greska");
		}
		
	});
}

function izmeniRestoran(id){
	window.location.href  ="/PorucivanjeHrane/restorani/"+'izmena/'+id; 
}

function dodajOmiljeni(id){
	const url = '/PorucivanjeHrane/rest/restaurants/'+id;
	$.ajax({
		type:'POST',
		url:url,
		success:function(data,status){
			window.location.reload();
		}
	});
}

function pretraziRestorane(){
	const naziv = $('#nazivPretraga').val();
	const adresa = $('#adresaPretraga').val();
	const kategorija = $('#kategorijaPretraga').val();
	
	const restoraniPretrage =restorani.filter( r => r.naziv.toUpperCase().includes(naziv.toUpperCase()) && 
											   r.adresa.toUpperCase().includes(adresa.toUpperCase()) &&
											   (kategorija=='' ?  true : r.kategorija == kategorija)
										);
	const list = $('#list');
	list.empty();
	
	restoraniPretrage.forEach(function (restoran, index , arr){
		generateElement(list,restoran)
	})
	
	
}