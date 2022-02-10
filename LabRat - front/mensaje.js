/*
	json={
		tipo="success/warning/danger/dark"
		titulo="Un mensaje"
		cuerpo="Este es un mensajito bonito"
		avatar="random/skeleton/gato/..."
	}
*/
function mostrarMensajito(json) {    
	document.getElementById("mensaje-titulo").innerHTML=json.titulo;
	document.getElementById("mensaje-cuerpo").innerHTML=json.cuerpo;
        if(json.avatar!=null){
            document.getElementById("mensaje-avatar").src=json.avatar;
            document.getElementById("mensaje-avatar").style.display="initial";
        }else{
            document.getElementById("mensaje-avatar").style.display="none";
        }
	var mensaje=document.getElementById("mensaje");	
	var color="#fff";
	var fontColor = "000";
	switch(json.tipo){
		case "dark":{
			color="#343a40";
			fontColor="#eee";
			break;
		}
		case "success":{
			color="#28a745";
			break;
		}
		case "warning":{
			color="#ffc107";
			break;
		}
		case "danger":{
			color="#dc3545";
			break;
		}
		case "info":{
			color="#17a2b8";
			fontColor="#aaa";
			break;
		}
	}
	mensaje.style.backgroundColor=color;
	mensaje.style.color=fontColor;
	mensaje.style.opacity=1;
    document.getElementById("mensaje").style.right="20px";
	setTimeout(ocultarMensajito,10000);
}

function ocultarMensajito() {
	document.getElementById("mensaje").style.opacity=0;
    setTimeout(document.getElementById("mensaje").style.right="2000px",2000);
}