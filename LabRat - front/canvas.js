var canvas = document.getElementById('simulador');
var ctx = canvas.getContext("2d");

var defaultCell = ctx.createLinearGradient(0, 0, 200, 200);
defaultCell.addColorStop(0, "white");
defaultCell.addColorStop(1, "#eee");

var defaultLine = ctx.createLinearGradient(0, 0, 400, 400);
defaultLine.addColorStop(0, "#bbb");
defaultLine.addColorStop(1, "#333");

var iniCell = "#00bb00";
var targetCell = "#aa0e0e"
var stepCell = "#e0cc25";
var returnCell = "#f50";

var repetidor;
var velocidad=150;

function cambiarVel(vel) {
	velocidad=vel;
}

function ejecutar(obj){
	clearInterval(repetidor);	
	crearCaja(12,12);
	avance=0;
	retorno=0;

	obj=JSON.parse(obj);
	pintarIniyFin();
	mover(obj);
}

function pintarIniyFin () {
	obj = currentObj;
	pintarInicial(obj.x1,obj.y1);
	pintarTarget(obj.x2,obj.y2);
}

function mover (obj) {
	repetidor = setInterval(function(){moverHasta(obj)}, velocidad);
}

var avance=0;
function moverHasta (obj) {
	json=obj.busqueda;
	if(avance<json.length){
		for (var j = 0; j <= avance; j++) {
			var cell = json[j];
			pintarPaso(cell.x,cell.y);	
		};

		pintarIniyFin();
		pintarLineas();

		var rata = json[avance];
		pintarRata(rata.x,rata.y);
		avance++;
	}else{
		clearInterval(repetidor);
		regresar(obj);
	}
}

function regresar (obj) {
	repetidor = setInterval(function(){regresarHasta(obj)}, velocidad);
}

var retorno=0;
function regresarHasta (obj) {
	json=obj.solucion;
	if(retorno<json.length){
		for (var j = 0; j <= retorno; j++) {
			var cell = json[j];
			pintarRegreso(cell.x,cell.y);	
		};

		pintarIniyFin();
		pintarLineas();

		var rata = json[retorno];
		pintarRata(rata.x,rata.y);
		retorno++;
	}else{
		clearInterval(repetidor);
	}
}


function crearCaja(x,y) {
	ctx.clearRect(0,0,360,360);
	for (var i = 0; i < x; i++) {
		for (var j = 0; j < y; j++) {
			ctx.fillStyle=defaultCell;
			ctx.fillRect(i*30,j*30,30,30);
			ctx.strokeStyle=defaultLine;			
		};
	};
	pintarLineas();
}

function pintarLineas(){
	for (var i = 0; i < 12; i++) {
		pintarLineaVertical(i*30);
		pintarLineaHorizontal(i*30);	
	};
}

function pintarLineaVertical (x) {
	ctx.beginPath();
	ctx.moveTo(x, 0);
	ctx.lineTo(x, 360);
	ctx.stroke();
}

function pintarLineaHorizontal (y) {
	ctx.beginPath();
	ctx.moveTo(0, y);
	ctx.lineTo(360,y);
	ctx.stroke();
}

function pintarInicial (x,y) {
	ctx.fillStyle=iniCell;
	ctx.fillRect(x*30,y*30,30,30);
}

function pintarRata(x,y){
	var img = document.getElementById("rata");
    var pat = ctx.createPattern(img, 'repeat');
    ctx.fillStyle = pat;
    ctx.fillRect(x*30,y*30,30,30);
}

function pintarTarget (x,y) {
	ctx.fillStyle=targetCell;
	ctx.fillRect(x*30,y*30,30,30);
	pintarQueso(x,y);
}

function pintarQueso(x,y){
	var img = document.getElementById("queso");
    var pat = ctx.createPattern(img, 'repeat');
    ctx.fillStyle = pat;
    ctx.fillRect(x*30,y*30,30,30);
}

function pintarPaso (x,y) {
	ctx.fillStyle=stepCell;
	ctx.fillRect(x*30,y*30,30,30);
}

function pintarRegreso (x,y) {
	ctx.fillStyle=returnCell;
	ctx.fillRect(x*30,y*30,30,30);
}