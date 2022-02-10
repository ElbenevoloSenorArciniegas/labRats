package modelo;

public class Caja {

    private int alto,ancho;
    private Rata rata;
    private Queso queso;

    public Caja(int ancho, int alto) {
        this.alto = alto;
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public Rata getRata() {
        return rata;
    }

    public void setRata(Rata rata) {
        this.rata = rata;
    }

    public Queso getQueso() {
        return queso;
    }

    public void setQueso(Queso queso) {
        this.queso = queso;
    }

    public boolean hayQueso(int x, int y){
        return x == this.queso.getX() && y == this.queso.getY();
    }

    public boolean puedeMover(int x, int y){
        return x >= 0 && x < this.ancho && y >=0 && y < this.alto;
    }

    public double olfatear(Estado e) {
        /*double x = e.getX()-queso.getX();
        double y = e.getY()-queso.getY();
        x = Math.pow(x,2);
        y = Math.pow(y,2);
        return Math.sqrt(x+y);*/
        return Math.abs(e.getX()-queso.getX()) + Math.abs(e.getY()-queso.getY());
    }

}
