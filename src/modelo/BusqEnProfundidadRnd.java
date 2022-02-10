package modelo;

import java.util.ArrayList;

public class BusqEnProfundidadRnd extends Rata{

    public BusqEnProfundidadRnd(Caja caja) {
        super(caja);
    }

    @Override
    public String buscar(){
        ArrayList<Estado> array = mover(getLastEstado());
        //return decirResultado(array);
        return this.estados.size()
                +"@"+decirResultado(this.estados)
                +"@"+array.size()
                +"@"+decirResultado(array);
    }

    public ArrayList<Estado> mover(Estado e){

        addEstado(e);

        if(chocar(e)){
            removeEstado(e);
            return null;
        }

        if(morder(e)){
            ArrayList<Estado> array = new ArrayList<>();
            e.setState("Comiendo");
            array.add(e);
            return array;
        }

        ArrayList<Estado> hijos = crearEstadosHijos(e);
        while(hijos.size()>0){
            int i = (int)(Math.random() * hijos.size());
            Estado hijo = hijos.get(i);
            //System.out.println(hijo.toString());
            if (!recordar(hijo)) { //vuelve a recordar para evitar pasar por estados conocidos en caso de estancarse
                ArrayList<Estado> array = mover(hijo);
                if (array != null) {
                    array.add(e);
                    return array;
                } else {
                    addEstado(e);
                }
            }
            hijos.remove(hijo);
        }
        return null;
    }
}
