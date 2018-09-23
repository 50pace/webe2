window.onload= function(){
	const url = window.location.pathname;
	const dostavljacIme = url.substring(url.lastIndexOf('/') + 1);
	const vozilaURL = '/PorucivanjeHrane/rest/deliverers/'+dostavljacIme+'/cars/0';
	
	$.ajax({
		type:'GET',
		url : vozilaURL,
		success: function(vozilo, status){
			const list = $('#list');
			if(status == 'nocontent'){
				const li = document.createElement('LI');
				li.innerHTML="Nema vozila";
				list.append(li);
				return;
			}
			
			const uloga = $('#uloga').val();

				let element ='<li><div class="media"></div>'+
				'<div class="media-left">'+
			    '<a href="#">'+
			      '<img class="media-object" style="width: 150px; height=150px;"src="https://audimediacenter-a.akamaihd.net/system/production/media/65020/images/40475897bd8320430abf787af6b9d82dbf1b2bbb/A188460_overfull.jpg?1531818710" >'+
			    '</a>'+
			  '</div>'+
			  '<div class="media-body">'+
			   '<h4 class="media-heading">' +'Marka: '+vozilo.marka+'<br>Model: '+vozilo.model +'<br>Registarska oznaka: '+vozilo.registarskaOznaka+'<br>Godina proizvodnje: '+vozilo.godinaProizvodnje
			   'Tip: '+vozilo.tip +'</h4>'+
			    '<p>'+vozilo.napomena+'</p>'+
			  '</div><div>'
			
			    if(uloga == 'Administrator'){
					 element +='<button  class="btn btn-warning" onClick="izmeniVozilo('+"'"+dostavljacIme +"',"+ vozilo.id+')">Izmeni</button>';
					 element +='&nbsp;<button  class="btn btn-danger" onClick="obrisiVozilo('+"'"+dostavljacIme +"',"+ vozilo.id+')">Obrisi</button>';
		 
				}   
				 element += '</div></div><li>';
				
				list.append(element);
		
			
		}
	});
	
	
}


function obrisiVozilo(dostavljacIme,voziloId){
	const voziloURL='/PorucivanjeHrane/rest/deliverers/'+dostavljacIme+'/cars/'+voziloId;
	
	$.ajax({
		type:'DELETE',
		url :voziloURL,
		success : function(data,status){
			window.location.reload();
		
		},
		error:function(error,status){
			console.log("greska");
		}
		
	});
}

function izmeniVozilo(dostavljacIme,voziloId){
	window.location.href  ="/PorucivanjeHrane/vozila/izmena/"+dostavljacIme+"/"+ voziloId; 
		
}