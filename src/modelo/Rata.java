package modelo;

import java.util.ArrayList;

public class Rata {

    public ArrayList<Estado> estados = new ArrayList<Estado>();
    public Caja caja;

    public Rata(Caja caja) {
        this.caja = caja;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public Estado getLastEstado(){
        return this.estados.get(this.estados.size()-1);
    }

    public void addEstado(Estado e){
        this.estados.add(e);
    }

    public void removeEstado(Estado e){
        this.estados.remove(e);
    }

    public int getX(){
        return getLastEstado().getX();
    }

    public int getY(){
        return getLastEstado().getY();
    }

    public String getState(){
        return getLastEstado().getState();
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public String nacer( int x, int y){
        addEstado(new Estado(x,y,"naciendo",null));
        return buscar();
    }

    public String buscar(){
        //Overriden
        return "";
    }

    public boolean morder(Estado e){
        return this.caja.hayQueso(e.getX(),e.getY());
    }

    public boolean chocar(Estado e){
        return !this.caja.puedeMover(e.getX(),e.getY());
    }

    public boolean recordar(Estado e){
        for (Estado est: estados) {
            if(est.equals(e)){
                return true;
            }
        }
        return false;
    }

    public String decirResultado(ArrayList<Estado> array){
        if(array!=null) {
            /*System.out.println("----------------------------------------------");
            System.out.println("-----------------FELICIDADES------------------");
            System.out.println("----------------------------------------------");
            for (modelo.Estado e : array) {
                System.out.println(e.toString());
            }*/
            String rta ="[";
            for (Estado e : array) {
                rta+="{\"x\":"+e.getX()+",\"y\":"+e.getY()+"},";
            }
            rta = rta.substring(0,rta.length()-1);
            rta += ']';
            return rta;
        }else{
            /*System.err.println("Lo siento, no puedo encontrar el queso");*/
            return "Lo siento, no puedo encontrar el queso";
        }
    }

    public ArrayList<Estado> crearEstadosHijos(Estado e) {
        ArrayList<Estado> hijos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int x = e.getX(), y = e.getY();
            switch (i) {
                case 0: {
                    x++;
                    break;
                }
                case 1: {
                    y++;
                    break;
                }
                case 2: {
                    x--;
                    break;
                }
                case 3: {
                    y--;
                    break;
                }
            }
            Estado hijo = new Estado(x, y, "moviendo", e);
            if (!recordar(hijo)) {
                hijos.add(hijo);
            }
        }
        return hijos;
    }

}
