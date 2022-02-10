/*
              -------Creado por-------
             \(x.x )/ Anarchy \( x.x)/
              ------------------------
 */

//    La segunda regla de Anarchy es no hablar de Anarchy  \\
/** Valida los campos requeridos en un formulario
 * Returns flag Devuelve true si el form cuenta con los datos mínimos requeridos
 */

var listado=[];
var currentObj=null;
var nAlgoritmos=5;

function buscarCamino (id,algoritmo) {
    for (var i = 0; i < listado.length; i++) {
        var obj=listado[i];
        if(obj.id == id){
            for (var j = 0; j < obj.algoritmos.length; j++) {
                var alg = obj.algoritmos[j];
                if(alg.id==algoritmo){
                    ejecutar(JSON.stringify(alg));
                }
            };
        }
    };
    pintarSeleccion(algoritmo);
}

function pintarSeleccion (algoritmo) {
    for (var i = 0; i < nAlgoritmos; i++) {
        document.getElementById("algoritmo"+i).classList.remove("w3-khaki");
    };
    document.getElementById("algoritmo"+algoritmo).classList.add("w3-khaki");   
}

function mostrarRandom(){
    var random = Math.round(Math.random() * (listado.length));
    mostrar(listado[random]);
}

function mostrar(obj){

    currentObj=obj;

    var algoritmoAleatorio = Math.round(Math.random() * obj.algoritmos.length);

    document.getElementById('x1').innerHTML =obj.x1;
    document.getElementById('y1').innerHTML =obj.y1;
    document.getElementById('x2').innerHTML =obj.x2;
    document.getElementById('y2').innerHTML =obj.y2;

    var html="";
    for (var i = 0; i < obj.algoritmos.length; i++) {
        var alg = obj.algoritmos[i];
        var name="";
        switch (alg.id){
            case 0:{
                name += "En profundidad";
                break;
            }
            case 1:{
                name += "En profundidad aleatoria";
                break;
            }
            case 2:{
                name += "Iterativa";
                break;
            }
            case 3:{
                name += "Iterativa aleatoria";
                break;
            }
            case 4:{
                name += "Heurística";
                break;
            }
        }

        var clase="";
        if (alg.id == algoritmoAleatorio){
            clase = "w3-khaki";
        }

        html+= '        <tr id="algoritmo'+alg.id+'" class="'+clase+'">'
                +'        <td><a href="javascript:buscarCamino('+obj.id+','+alg.id+')">'+name+'</a></td>'
                +'        <td>'+alg.sizeBusq+'</td>'
                +'        <td>'+alg.sizeSol+'</td>'
                +'      </tr>'
        
    };
    document.getElementById("infoBody").innerHTML=html;
    buscarCamino(obj.id,algoritmoAleatorio);
}

function preList(){
  mostrarMensajito(
      {tipo:"dark",
      titulo:"Zort!",
      cuerpo:"Oye, Cerebro ¿qué vamos a hacer esta noche?",
      avatar:"pinkyDark.png"});
     //Solicite información del servidor
 	enviar("","/labratBack/listAll",postList); 
}

 function postList(result,state){
     //Maneje aquí la respuesta del servidor.
     if(state=="success"){
         var json=JSON.parse(result);

mostrarMensajito(
      {tipo:"dark",
      titulo:"Narf!",
      cuerpo:"Mira, Cerebro. Estoy buscando esas deliciosas bolitas de queso mohoso.",
      avatar:"pinkyDark.png"});

        parseSoluciones(json);

        mostrarRandom();
     }else{
         alert("Hubo un errror interno ( u.u)\n"+result);
     }
}

function preSelect () {
    var formData=$('#trainer').serialize();
    mostrarMensajito(
      {tipo:"dark",
      titulo:"Troz!",
      cuerpo:"¡Ja, ja, ja! ¡Mira esos botones bonitos, tienen lucecitas! ¡Quiero tocarlos!",
      avatar:"pinkyDark.png"});
    enviar(formData,"/labratBack/select",postSelect);
}

 function postSelect(result,state){
     //Maneje aquí la respuesta del servidor.
     if(state=="success"){
         var json=JSON.parse(result);
  
  mostrarMensajito(
      {tipo:"dark",
      titulo:"Poit!",
      cuerpo:"¡Qué bien, Cerebro! Los del laboratorio nos dejaron otro divertido laberinto.",
      avatar:"pinkyDark.png"});

         parseSoluciones(json);
         mostrar(listado[listado.length-1]);
     }else{
         alert("Hubo un errror interno ( u.u)\n"+result);
     }
}

function parseSoluciones(json){
     for(var i=0; i < Object.keys(json).length; i=i+nAlgoritmos) {
      var nuevo = {};
        nuevo.id= json[i].id;
        nuevo.x1= json[i].x1;
        nuevo.y1= json[i].y1;
        nuevo.x2= json[i].x2;
        nuevo.y2= json[i].y2;
        nuevo.algoritmos = [];
        for (var j = 0; j < nAlgoritmos; j++) {
          var obj = json[i+j];
          var alg = {};
          alg.id = obj.algoritmo;
          alg.sizeBusq = obj.sizeBusq;
          alg.busqueda = obj.busqueda;
          alg.sizeSol = obj.sizeSol;
          alg.solucion = obj.solucion;
          nuevo.algoritmos.push(alg);
        };
        listado.push(nuevo);  
     }
}