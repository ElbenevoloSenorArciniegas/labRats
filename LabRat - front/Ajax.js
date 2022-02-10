/*
              -------Creado por-------
             \(x.x )/ Anarchy \( x.x)/
              ------------------------
 */

//    ¡Me han encontrado! ¡No sé cómo pero me han encontrado!  \\
        var READY_STATE_UNINITIALIZED=0; 
        var READY_STATE_LOADING=1; 
        var READY_STATE_LOADED=2;
        var READY_STATE_INTERACTIVE=3; 
        var READY_STATE_COMPLETE=4;
         
        var peticion_http; 
        var divDestino; 

        function cargaContenido(container,url) {
          peticion_http = inicializa_xhr();
         divDestino=container;
          if(peticion_http) {
            peticion_http.onreadystatechange = muestraContenido;
            peticion_http.open('get', url, true);
            peticion_http.send(null);
          }
        }
         
        function inicializa_xhr() {
          if(window.XMLHttpRequest) {
            return new XMLHttpRequest();
          }
          else if(window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
          }
        }
         
        function muestraContenido() {
          if(peticion_http.readyState == READY_STATE_COMPLETE) {
            if(peticion_http.status == 200) {
              document.getElementById(divDestino).innerHTML=peticion_http.responseText;
            } else if(peticion_http.status == 404) {
              document.getElementById(divDestino).innerHTML=peticion_http.responseText;
            } 
          }
        }

    function enviar(formData,url,func){
     
	 $.post(url,formData, function(result,state){
         func(result,state);
     });    
    }

//That´s all folks!