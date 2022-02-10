package control;

import data.Conexion;
import modelo.*;

public class Trainer {

    public static void entrenarRandom(int n){
        int max=10;
        for (int i = 0; i < n; i++) {
            //int ancho = (int) (Math.random() * max)+2;
            //int alto = (int) (Math.random() * max)+2;
            int ancho = 12;
            int alto = 12;
            Caja caja = new Caja(ancho,alto);

            int x2 = (int) (Math.random() * ancho);
            int y2 = (int) (Math.random() * alto);
            Queso queso = new Queso(x2,y2);
            caja.setQueso(queso);

            int x1=(int) (Math.random() * ancho);
            int y1=(int) (Math.random() * alto);

            hagale(caja,x1,y1,x2,y2,ancho,alto);
        }
        Conexion.cerrar();
    }

    public static String entrenar(int x1,int y1, int x2, int y2){
        int max=10;
        //int ancho = (int) (Math.random() * max)+2;
        //int alto = (int) (Math.random() * max)+2;
        int ancho = 12;
        int alto = 12;
        Caja caja = new Caja(ancho,alto);

        Queso queso = new Queso(x2,y2);
        caja.setQueso(queso);

        return hagale(caja,x1,y1,x2,y2,ancho,alto);
    }

    private static String hagale(Caja caja, int x1,int y1, int x2, int y2, int ancho, int alto){
        String rta="[";
        for (int i = 0; i < 5; i++) {
            Rata rata;
            switch (i){
                case 0:{
                    rata = new BusqEnProfundidad(caja);
                    break;
                }
                case 1:{
                    rata = new BusqEnProfundidadRnd(caja);
                    break;
                }
                case 2:{
                    rata = new BusqIterativa(caja);
                    break;
                }
                case 3:{
                    rata = new BusqIterativaRnd(caja);
                    break;
                }
                case 4:{
                    rata = new BusqHeuristica(caja);
                    break;
                }
                default:{
                    rata= new Rata(caja);
                }
            }
            rta += mandar(x1,y1,x2,y2,i,rata.nacer(x1,y1))+",";
        }
        rta = rta.substring(0,rta.length()-1);
        rta+="]";
        return rta;
    }

    private static String mandar(int x1,int y1, int x2, int y2, int algoritmo, String s){
        String[] split = s.split("@");
        int sizeBusq=Integer.parseInt(split[0]);
        String busq=split[1];
        int sizeSol=Integer.parseInt(split[2]);
        String solucion=split[3];
        return Conexion.insert(x1,y1,x2,y2,algoritmo,sizeBusq,busq,sizeSol,solucion);
    }

}
