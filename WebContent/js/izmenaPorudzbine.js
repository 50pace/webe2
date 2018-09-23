
var korpaArtikala = [];
var korpaKolicina = [];
var artikli= [];
var buyerUsername;
var porudzbinaId;
var bodovi;
window.onload= function(){
	const url = window.location.href;
	let urlBack = "";
	
	const splitURL = url.split('/');
	buyerUsername = splitURL[splitURL.length - 3];
	const restoranId = splitURL[splitURL.length - 2];
	porudzbinaId = splitURL[splitURL.length - 1];
	const artiklURL = '/PorucivanjeHrane/rest/restaurants/'+restoranId+'/articles';
	
	
	
	$.ajax({
		type:'GET',
		url : artiklURL,
		success: function(data, status){
			const list = $('#list');
			artikli = data;
			data.forEach(function (artikl, index, arr){
				generateElement(list, artikl)
			});
			
			const porURL = '/PorucivanjeHrane/rest/buyers/'+buyerUsername+'/orders/'+porudzbinaId;
			$.ajax({
				type:'GET',
				url : porURL,
				success: function(data, status){
					data.stavke.forEach( (stavka, index, arr) =>{
						$('#kolicina'+stavka.id).val(data.kolicine[index]);
						dodajUKorpu(stavka.id);
					})
					
					$('#napomena').val(data.napomena);
					$('#kupac').val(buyerUsername);
					$('#dostavljac').val(data.dostavljacUsername);
					$('#statusPorudzbine').val(data.status);
					bodovi = data.utrosenoBodova;
				}
			});
		}
	});
	
	const korURL = '/PorucivanjeHrane/rest/korisnici';
	$.ajax({
		type:'GET',
		url : korURL,
		success : function(data,status){
				const listaKorisnika=$('#korisnici');
				const uloga = $('#uloga').val();
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
	});
	

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
   '<h4 class="media-heading">'+artikl.naziv+'&nbsp; Kolicina:'+artikl.kolicina+jedinica+'&nbsp; Cena:' + artikl.kolicina*artikl.jedinicnaCena +'</h4>'+
    '<p>'+artikl.opis+'</p>'+
  '</div><div>'


	element +='<input type="number" id="kolicina'+artikl.id+'">';
	element +='&nbsp;<button  class="btn btn-success" onClick="dodajUKorpu('+artikl.id+')">Dodaj u korpu</button>'; 
	element += '</div></div><li>';
	
	list.append(element);
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

	label.innerHTML = artiklId +"; kolicina: "+kolicina ;
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

function izmeni(){
	if(korpaArtikala.length == 0){
		return;
	}
	
	const body ={};
	body.stavke = korpaArtikala;
	body.kolicine = korpaKolicina;
	body.napomena = $('#napomena').val();
	body.utrosenoBodova = bodovi;
	body.kupacUsername = $('#kupac').val();
	body.dostavljacUsername = $('#dostavljac').val();
	body.status = $('#statusPorudzbine').val();

	
	const url = '/PorucivanjeHrane/rest/buyers/'+buyerUsername+'/orders/'+porudzbinaId;
	$.ajax({
		type:'PUT',
		url:url,
		contentType :'application/json',
		data : JSON.stringify(body),		
		success: (data, status) => {
			alert("Uspesno ste izmenili porudzbinu");
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