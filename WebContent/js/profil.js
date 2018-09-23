var porudzbine = [];
window.onload = function(){
	
	const url = window.location.pathname;
	const username = url.substring(url.lastIndexOf('/') + 1 );
	const uloga = $('#uloga').val();
	if(uloga == "Kupac"){
		const korisnikURL = '/PorucivanjeHrane/rest/kupci/'+ username;
		
		$.ajax({
			type:'GET',
			url : korisnikURL,
			success: function(kupac,status){
				const listaPorudzbina = $('#listaPorudzbina');
				listaPorudzbina.append(document.createElement('br'));
				porudzbine = kupac.porudzbine;
				kupac.porudzbine.forEach(function (porudzbina, index, arr){
					if(porudzbina.obrisana){
						return;
					}
					const li=document.createElement('LI');
					const label=document.createElement('LABEL');
					label.innerHTML=porudzbina.id + ' ' + porudzbina.datumVreme.dayOfMonth + '.' + 
					porudzbina.datumVreme.monthValue +'.'+ 
					porudzbina.datumVreme.year + '. ' +
					porudzbina.datumVreme.hour  + ':' +
					porudzbina.datumVreme.minute+ ' ' + porudzbina.status + ' ' + porudzbina.napomena;
					li.appendChild(label);
					if(porudzbina.status == "Poruceno"){
						const button=document.createElement('BUTTON');
						button.innerHTML="Otkazi porudzbinu";
						button.className = 'btn btn-success';
						button.style.marginLeft='70px';
						button.addEventListener("click",function(){
							otkaziPorudzbinu(porudzbina.id)
						})
						li.appendChild(button);

					}
					
					listaPorudzbina.append(li);
					listaPorudzbina.append(document.createElement('br'));
				});
				
				
				const listaRestorana = $('#listaRestorana');
				listaRestorana.append(document.createElement('br'));
				kupac.omiljeniRestorani.forEach(function (restoran, index, arr){
					const li = document.createElement('LI');
					const a = document.createElement('A');
					a.href = '/PorucivanjeHrane/restorani/'+restoran.id;
					a.innerHTML = restoran.naziv;
					li.appendChild(a);
					listaRestorana.append(li);
				});
				
				
			}
		});
	}else if(uloga == "Dostavljac"){
		const dostavljacURL = '/PorucivanjeHrane/rest/deliverers/'+ username;

		$.ajax({
			type:'GET',
			url : dostavljacURL,
			success: function(dostavljac,status){
				const listaPorudzbina = $('#porudzbineDostavljaca');
				listaPorudzbina.append(document.createElement('br'));
				porudzbine = dostavljac.dodeljenePorudzbine;
				dostavljac.dodeljenePorudzbine.forEach(function (porudzbina, index, arr){
					if(porudzbina.obrisana){
						return;
					}
					const li=document.createElement('LI');
					const label=document.createElement('LABEL');
					label.innerHTML=porudzbina.id + ' ' + new Date(porudzbina.datumVreme) + ' ' + porudzbina.status + ' ' + porudzbina.napomena;
					li.appendChild(label);
					if(porudzbina.status == "Dostava_u_toku"){
						const button=document.createElement('BUTTON');
						button.innerHTML="Dostavi porudzbinu";
						button.className = 'btn btn-success';
						button.style.marginLeft='70px';
						button.addEventListener("click",function(){
							dostaviPorudzbinu(porudzbina.id,porudzbina.kupacUsername)
						})
						li.appendChild(button);

					}
					
					listaPorudzbina.append(li);
					listaPorudzbina.append(document.createElement('br'));
				});
				
			
				
			}
		});
	}
	
}

function dostaviPorudzbinu(id,kupacUsername){
	const porudzbina=porudzbine.find(p => p.id == id && p.kupacUsername==kupacUsername);

	const porURL = '/PorucivanjeHrane/rest/buyers/'+kupacUsername+'/orders/finish/'+id;
	$.ajax({
		type:'PUT',
	    url : porURL,
	    success:function(data,status){
	    	window.location.reload();
	    }
	})
}

function otkaziPorudzbinu(id){
	const porudzbina=porudzbine.find(p => p.id == id);

	const porURL = '/PorucivanjeHrane/rest/buyers/'+porudzbina.kupacUsername+'/orders/cancel/'+id;
	$.ajax({
		type:'PUT',
	    url : porURL,
	    success:function(data,status){
	    	window.location.reload();
	    }
	})
	
}