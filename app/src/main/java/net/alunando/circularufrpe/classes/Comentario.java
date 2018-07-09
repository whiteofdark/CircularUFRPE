package net.alunando.circularufrpe.classes;

public class Comentario {
    private int id;
    private Post post;
    Usuario usuario = new Usuario();
    private String data;
    private String hora;
    private String texto;

    public Comentario(){}

    public Comentario(int id, Post post, Usuario usuario, String data, String hora, String texto) {
        this.id = id;
        this.post = post;
        this.usuario = usuario;
        this.data = data;
        this.hora = hora;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
