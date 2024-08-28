package Models;

public class Cliente extends Usuario{
    String endereco;
    public Cliente(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha);
        this.endereco = endereco;
    }
}
