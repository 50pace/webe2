var porudzbine=[];

window.onload=function(){
	
	const porudzbineURL = '/PorucivanjeHrane/rest/orders';
	$.ajax({
		type:'GET',
		url:porudzbineURL,
		success:function(data,status){
			const listaPorudzbina = $('#porudzbine');
			listaPorudzbina.append(document.createElement('br'));
			const uloga = $('#uloga').val();
			if(uloga != 'Kupac'){
				porudzbine=data;
				data.forEach(function(porudzbina,index,array){
					const li=document.createElement('LI');
					const label=document.createElement('LABEL');
					label.innerHTML=porudzbina.id + ' ' +porudzbina.datumVreme.dayOfMonth + '.' + 
					porudzbina.datumVreme.monthValue +'.'+ 
					porudzbina.datumVreme.year + '. ' +
					porudzbina.datumVreme.hour  + ':' +
					porudzbina.datumVreme.minute+ ' ' +
					porudzbina.status + ' ' + porudzbina.napomena;
					li.appendChild(label);
					
					if(uloga =="Dostavljac" && porudzbina.status == "Poruceno"){
						const button=document.createElement('BUTTON');
						button.innerHTML="Preuzmi porudzbinu";
						button.className = 'btn btn-success';
						button.style.marginLeft='70px';
						button.addEventListener("click",function(){
							preuzmiPorudzbinu(porudzbina.id)
						})
						li.appendChild(button);

					}

					if(uloga == "Administrator"){
						const button=document.createElement('BUTTON');
						button.innerHTML="Izmeni porudzbinu";
						button.className = 'btn btn-warning';
						button.style.marginRight='20px';
						button.style.marginLeft='70px';
						button.addEventListener("click",function(){
							izmeniPorudzbinu(porudzbina.id)
						})
						li.appendChild(button);
						
						const button2=document.createElement('BUTTON');
						button2.innerHTML="Obrisi porudzbinu";
						button2.className = 'btn btn-danger';
						button2.addEventListener("click",function(){
							obrisiPorudzbinu(porudzbina.id)
						})
						li.appendChild(button2);

					}
					
					listaPorudzbina.append(li);
					listaPorudzbina.append(document.createElement('br'));
				});
			}
		}
	});
}

function preuzmiPorudzbinu(id){
	const porudzbina=porudzbine.find(p => p.id == id);

	const porURL = '/PorucivanjeHrane/rest/buyers/'+porudzbina.kupacUsername+'/orders/take/'+id;
	$.ajax({
		type:'PUT',
	    url : porURL,
	    success:function(data,status){
	    	window.location.reload();
	    },
	    error: function (error, status){
	    	alert("Vec imate porudzbinu u toku")
	    }
	})
}


function obrisiPorudzbinu(id){
	const porudzbina=porudzbine.find(p => p.id == id);
	const porURL = '/PorucivanjeHrane/rest/buyers/'+porudzbina.kupacUsername+'/orders/'+id;
	$.ajax({
		type:'DELETE',
		url:porURL,
		success:function(data,status){
			window.location.reload();
		}
		
	});
}

function izmeniPorudzbinu(id){
	const porudzbina=porudzbine.find(p => p.id == id);
	const porURL = '/PorucivanjeHrane/rest/buyers/'+porudzbina.kupacUsername+'/orders/'+id;
	window.location.href ='/PorucivanjeHrane/porudzbina/izmena/'+porudzbina.kupacUsername +"/"
		+porudzbina.stavke[0].restoranId +"/"+porudzbina.id;

}