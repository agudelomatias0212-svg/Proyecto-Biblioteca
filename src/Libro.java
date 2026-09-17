public class Libro extends MaterialBibliografico {

    private String genero;
    private String codigo;
    private int añopublicado;
    private int copiasDisponibles;

    public Libro (String titulo, String autor, String codigo, String genero, int añopublicado, int copiasDisponibles) {

        super (titulo,autor);

        this.codigo = codigo;
        this.genero = genero;
        this.añopublicado = añopublicado;
        this.copiasDisponibles = copiasDisponibles;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo (String codigo) {
        this.codigo = codigo;
    }

    public String getGenero (){
        return genero;
    }

    public  void setGenero (String genero) {
        this.genero = genero;
    }

    public int getañopublicado () {
        return añopublicado;
    }

    public void setañopublicado (int añopublicado) {
        this.añopublicado = añopublicado;
    }

    public int getcopiasDisponibles () {
        return copiasDisponibles;
    }

    public void setcopiasDisponibles (int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    @Override
    public String toString() {
        return "libro{" +
                "titulo='" + getTitulo() + '\'' + ", autor ='" +
                getAutor() + '\'' + ", codigo='" + codigo + '\'' +
                ", genero='" + genero + '\'' + ", añopublicado='" +
                añopublicado + ", copiasDisponibles='" + copiasDisponibles +
                '}';

    }
}
