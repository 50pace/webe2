
var korpaArtikala = [];
var korpaKolicina = [];
var artikli= [];

window.onload= function(){
	const url = window.location.href;
	let urlBack = "";
	if(url.includes('pretrazi')){
		const urlSplit = url.split('?');
		const query = urlSplit[urlSplit.length - 1];
		urlBack = '/PorucivanjeHrane/rest/search?'+query; 
	}else{
		const id = url.substring(url.lastIndexOf('/') + 1);
		urlBack = '/PorucivanjeHrane/rest/restaurants/'+id+'/articles';
	}
	
	
	$.ajax({
		type:'GET',
		url : urlBack,
		success: function(data, status){
			const list = $('#list');
			artikli = data;
			data.forEach(function (artikl, index, arr){
				generateElement(list, artikl)
			});
			
		}
	});
	const uloga = $('#uloga').val();
	if(uloga == 'Administrator'){
		const korURL = '/PorucivanjeHrane/rest/korisnici';
		$.ajax({
			type:'GET',
			url : korURL,
			success : function(data,status){
					const listaKorisnika=$('#korisnici');
					const uloga = $('#uloga').val();
					if(uloga == 'Administrator'){
						const selectKupac = $('#kupac');
						const selectDostavljac = $('#dostavljac');
						data.forEach(function(korisnik,index,arr){
							const option = document.createElement('OPTION');
							option.value = korisnik.korisnickoIme;
							option.innerHTML = korisnik.korisnickoIme;
						
							if(korisnik.uloga == 'Kupac'){
								selectKupac.append(option);
							}else if(korisnik.uloga == 'Dostavljac'){
								selectDostavljac.append(option);
							}
						})
					}
				}
		});
	}
	
	
}

function generateElement(list, artikl){
	const uloga = $('#uloga').val();
	let jedinica = 'g';
	if(artikl.tip == 'Pice'){
		jedinica = 'ml';
	}
	
	let element ='<li><div class="media"></div>'+
	'<div class="media-left">'+
    '<a href="#">'+
      '<img class="media-object" style="width: 150px; height=150px;"src="https://www.goodfood.com.au/content/dam/images/h/0/f/a/q/i/image.related.wideLandscape.940x529.h0fa4n.png/1515456591895.jpg" >'+
    '</a>'+
  '</div>'+
  '<div class="media-body">'+
   '<h4 class="media-heading">'+artikl.naziv+'<br> Kolicina:'+artikl.kolicina+jedinica+'&nbsp; Cena:' + artikl.kolicina*artikl.jedinicnaCena +'</h4>'+
    '<p>'+artikl.opis+'</p>'+
  '</div><div>'

    if(uloga == 'Administrator'){
		 element +='<button  class="btn btn-warning" onClick="izmeniArtikl('+artikl.restoranId +','+ artikl.id+')">Izmeni</button>';
		 element +='&nbsp;<button  class="btn btn-danger" onClick="obrisiArtikl('+artikl.restoranId +','+ artikl.id+')">Obrisi</button>';
		 element +='<br><br><input type="number" id="kolicina'+artikl.id+'">';
		 element +='&nbsp;<button  class="btn btn-success" onClick="dodajUKorpu('+artikl.id+')">Dodaj u korpu</button>';
	}
	if(uloga =='Kupac'){
		element +='<input type="number" id="kolicina'+artikl.id+'">';
		element +='&nbsp;<button  class="btn btn-success" onClick="dodajUKorpu('+artikl.id+')">Dodaj u korpu</button>';
	}  
	
	
	 element += '</div></div><li>';
	
	list.append(element);
}


function obrisiArtikl(restoranId, artiklId){
	const artiklURL='/PorucivanjeHrane/rest/restaurants/'+restoranId+'/articles/'+artiklId;
	
	$.ajax({
		type:'DELETE',
		url :artiklURL,
		success : function(data,status){
			window.location.reload();
		},
		error:function(error,status){
			console.log("greska");
		}
		
	});
}

function izmeniArtikl(restoranId, artiklId){
	window.location.href  ="/PorucivanjeHrane/artikli/izmena/"+restoranId+"/"+ artiklId; 
		
}

function dodajUKorpu(artiklId){
	const provera = korpaArtikala.find(a => a.id == artiklId);
	if(provera){
		return;
	}
	const artikl = artikli.find(a => a.id == artiklId);
	const kolicina = $('#kolicina'+artiklId).val();
	if(kolicina<=0){
		return;
	}
	const a ={};
	a.id = artikl.id;
	a.naziv=artikl.naziv;
	a.jedinicnaCena=artikl.jedinicnaCena;
	a.opis=artikl.opis;
	a.kolicina=artikl.kolicina;
	a.tip=artikl.tip;
	korpaArtikala.push(a);
	korpaKolicina.push(kolicina);
	
	
	const li = document.createElement('LI');
	li.id = 'li'+artiklId;
	const label = document.createElement('LABEL');

	label.innerHTML = artiklId +' '+a.naziv+"; kolicina: "+kolicina ;
	const button =  document.createElement('BUTTON');
	button.innerHTML = 'Ukloni';
	button.className = 'btn btn-danger';
	button.style.marginLeft='20px';
	button.addEventListener('click', function(){
		obrisiIzKorpe(artiklId);
	});
	li.appendChild(label);
	li.appendChild(button);
	$('#korpa').append(li);
	$('#korpa').append(document.createElement('br'));
}

function obrisiIzKorpe(artiklId){
	const index = korpaArtikala.findIndex(a=> a.id == artiklId);
	korpaArtikala.splice(index, 1);
	korpaKolicina.splice(index, 1);
	const li = document.getElementById('li'+artiklId);
	li.parentNode.removeChild(li);

}

function poruci(){
	if(korpaArtikala.length == 0){
		return;
	}
	
	const body ={};
	body.stavke = korpaArtikala;
	body.kolicine = korpaKolicina;
	body.napomena = $('#napomena').val();
	body.utrosenoBodova = $('#bodovi').val();
	const uloga = $('#uloga').val();
	if(uloga == 'Administrator'){
		body.kupacUsername = $('#kupac').val();
		body.dostavljacUsername = $('#dostavljac').val();
	}
	
	const url = '/PorucivanjeHrane/rest/orders';
	$.ajax({
		type:'POST',
		url:url,
		contentType :'application/json',
		data : JSON.stringify(body),		
		success: (data, status) => {
			alert("Uspesno ste dodali porudzbinu");
			window.location.href = "/PorucivanjeHrane";
		}
	});
}

function pretraziArtikle(){
	const naziv=$('#nazivPretraga').val();
	const tip=$('#tipPretraga').val();
	const pocetnaCijena=$('#pocetnaCijena').val();
	const krajnjaCijena=$('#krajnjaCijena').val();
	
	const artikliPretrage=artikli.filter( a => a.naziv.toUpperCase().includes(naziv.toUpperCase()) && 
									     (tip=='' ?  true : a.tip == tip) &&
									     (pocetnaCijena=='' ? true : a.kolicina *a.jedinicnaCena >= pocetnaCijena) &&
									     (krajnjaCijena=='' ? true : a.kolicina *a.jedinicnaCena <= krajnjaCijena) 
								);
	
	const list = $('#list');
	list.empty();
	
	artikliPretrage.forEach(function (artikl, index , arr){
		generateElement(list,artikl);
	})
}