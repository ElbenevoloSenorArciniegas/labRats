package modelo;

import java.util.ArrayList;

public class BusqHeuristica extends Rata{

    public BusqHeuristica(Caja caja) {
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

        ArrayList<Estado> hijos = ordenar(crearEstadosHijos(e));
        //ordena a los hijos dependiendo de qué tan lejos estén del queso

        for (Estado hijo: hijos) {
            //System.out.println(hijo.toString());
            if (!recordar(hijo)) { //vuelve a recordar para evitar pasar por estados conocidos en caso de estancarse
                ArrayList<Estado> array = mover(hijo);
                if (array != null) {
                    array.add(e);
                    return array;
                } else {
                    //addEstado(e);
                }
            }
        }
        return null;
    }

    private ArrayList<Estado> ordenar(ArrayList<Estado> hijos){
        for (int i = 0; i < hijos.size() ; i++) {
            double d1 = olfatear(hijos.get(i));
            for (int j = i; j < hijos.size(); j++) {
                double d2 = olfatear(hijos.get(j));
                if(d2 < d1){
                    Estado aux= hijos.get(i);
                    hijos.set(i,hijos.get(j));
                    hijos.set(j,aux);
                }
            }
        }
        return hijos;
    }

    private double olfatear(Estado e){
        return this.getCaja().olfatear(e);
    }

}
