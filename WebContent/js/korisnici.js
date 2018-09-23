var korisnici = [];

window.onload=function(){
	
	const korURL = '/PorucivanjeHrane/rest/korisnici';
	$.ajax({
		type:'GET',
		url : korURL,
		success : function(data,status){
				const listaKorisnika=$('#korisnici');
				const uloga = $('#uloga').val();
				if(uloga == 'Administrator'){
					korisnici= data;
					data.forEach(function(korisnik,index,arr){
						const li=document.createElement('LI');
						const label=document.createElement('LABEL');
						const button=document.createElement('BUTTON');
						button.innerHTML="Promijeni tip korisnika";
						button.style.marginLeft='50px';
						button.addEventListener("click",function(){
							promijeniTip(korisnik.korisnickoIme);
						})
						label.innerHTML=korisnik.korisnickoIme + '&nbsp &nbsp &nbsp &nbsp ';
						const select=document.createElement('SELECT');
						select.id = "select"+korisnik.korisnickoIme;
						const optionKupac = document.createElement('OPTION');
						const optionAdministrator = document.createElement('OPTION');
						const optionDostavljac = document.createElement('OPTION');
						optionKupac.value = "Kupac";
						optionKupac.innerHTML="Kupac";
						optionAdministrator.value = "Administrator";
						optionAdministrator.innerHTML="Administrator";
						optionDostavljac.value = "Dostavljac";
						optionDostavljac.innerHTML="Dostavljac";
						if(korisnik.uloga != "Kupac"){
							select.appendChild(optionKupac);
						}
						if(korisnik.uloga != "Dostavljac"){
							select.appendChild(optionDostavljac);
						}
						if(korisnik.uloga != "Administrator"){
							select.appendChild(optionAdministrator);
						}
						
						li.appendChild(label);
						li.appendChild(select);
						li.appendChild(button);
						listaKorisnika.append(li);
					})
				}
			}
	});
	
}

function promijeniTip(korisnickoIme){
	const korisnikURL = '/PorucivanjeHrane/rest/korisnici/'+korisnickoIme;
	const korisnik = korisnici.find(k => k.korisnickoIme == korisnickoIme);
	korisnik.uloga = $("#select"+korisnickoIme).val();
	$.ajax({
		type:'PUT',
	    url : korisnikURL,
	    contentType:'application/json',
	    data:JSON.stringify(korisnik),
	    success:function(data,status){
	    	window.location.reload();
	    }
	});
}