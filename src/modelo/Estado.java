package modelo;

public class Estado {

    private int x,y;
    private String state;
    private Estado padre;

    public Estado(int x, int y, String state, Estado padre) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.padre = padre;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Estado getPadre() {
        return padre;
    }

    public void setPadre(Estado padre) {
        this.padre = padre;
    }

    @Override
    public String toString() {
        String p="ROOT";
        if(padre!=null){
            p = padre.getX() +" , "+ padre.getY();
        }
        return "modelo.Estado{"+ x +" , " + y +" "+ state + ' ' + ", padre= " +p+"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estado estado = (Estado) o;
        return x == estado.x &&
                y == estado.y;
    }

}
