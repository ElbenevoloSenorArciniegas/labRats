package modelo;

import java.util.ArrayList;

public class BusqIterativaRnd extends Rata{

    private int level;
    private Estado root;
    //private ArrayList<Estado> auxiliar= new ArrayList<>(); //despreciado porque es demasiado detallado con la búsqueda


    public BusqIterativaRnd(Caja caja) {
        super(caja);
    }

    @Override
    public String buscar(){
        level = 1; //me ahorro la iteración nula del fatídico e hipotético nivel 0
        root = getLastEstado();
        ArrayList<Estado> array = mover(root,1);
        while (array == null){
            level++;
            //System.err.println("--------------------NEW LEVEL: "+level);
            estados.clear();
            estados.add(root);
            array = mover(root,1);
        }
        return this.estados.size()
                +"@"+decirResultado(this.estados)
                +"@"+array.size()
                +"@"+decirResultado(array);
        //return decirResultado(estados);
    }

    public ArrayList<Estado> mover(Estado pivot, int nivel){
        if(nivel>level){
            return null;
        }

        if(chocar(pivot)){
            removeEstado(pivot);
            return null;
        }

        if(morder(pivot)){
            ArrayList<Estado> array = new ArrayList<>();
            pivot.setState("Comiendo");
            array.add(pivot);
            return array;
        }

        ArrayList<Estado> hijos = crearEstadosHijos(pivot);
        ArrayList<Estado> copia = new ArrayList<>();
        for (Estado hijo: hijos) {
            copia.add(hijo);
        }
        while(copia.size()>0){
            int i = (int)(Math.random() * copia.size());
            Estado hijo = copia.get(i);
            //System.out.println(hijo.toString());
            if(!chocar(hijo)){
                addEstado(hijo);
                if(morder(hijo)) {
                    hijo.setState("Comiendo");
                    ArrayList<Estado> array = new ArrayList<>();
                    array.add(hijo);
                    array.add(pivot);
                    return array;
                }
                addEstado(pivot);
            }
            copia.remove(hijo);
        }
        while(hijos.size()>0){
            int i = (int)(Math.random() * hijos.size());
            Estado hijo = hijos.get(i);
            ArrayList<Estado> array=mover(hijo,nivel+1);
            if(array!=null) {
                array.add(pivot);
                return array;
            }else{
                addEstado(pivot);
            }
            hijos.remove(hijo);
        }
        return null;
    }
/*
    @Override
    public void addEstado(Estado e) {
        super.addEstado(e);
        this.auxiliar.add(e);
    }

    @Override
    public void removeEstado(Estado e) {
        super.removeEstado(e);
        this.auxiliar.remove(e);
    }*/
}
