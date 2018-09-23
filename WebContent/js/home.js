window.onload= function(){
	const url = '/PorucivanjeHrane/rest/popularArticles';
	
	$.ajax({
		type : 'GET',
		url : url,
		success : function (artikli , status){
			const listaPica = $('#popularnaPica');
			const listaJela = $('#popularnaJela');
			artikli.forEach(function (artikl, index, arr){
				const li = document.createElement('LI');
				li.innerHTML = 'Id: '+artikl.id+' &nbsp &nbsp &nbsp &nbsp Naziv: '+ artikl.naziv+ ' ,Tip: ' + artikl.tip;
				if(artikl.tip == "Jelo"){
					listaJela.append(li);
				}else{
					listaPica.append(li);
				}
			})
		}
	});
}