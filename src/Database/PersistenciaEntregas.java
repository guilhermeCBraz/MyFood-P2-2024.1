package Database;

import Models.Empresa;
import Models.Entrega;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PersistenciaEntregas {
    static String caminho = "src/Database/Entregas.xml";


    public static void gravarDados(HashMap<Integer, Entrega> mapUsuarios2){

        try (FileOutputStream fos = new FileOutputStream(caminho);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(mapUsuarios2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, Entrega> carregarDados() {
        HashMap<Integer, Entrega> userMap = null;

        // Usando XMLDecoder para ler o arquivo XML e reconstruir o HashMap
        try (FileInputStream fis = new FileInputStream(caminho);
             XMLDecoder decoder = new XMLDecoder(fis)) {

            // Lendo o objeto (que será um HashMap)
            userMap = (HashMap<Integer, Entrega>) decoder.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userMap;
    }

    //Deixar xml em branco
    public static void apagarDados() {
        try (FileOutputStream fos = new FileOutputStream(caminho);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            // Não gravamos nenhum objeto, resultando em um arquivo XML vazio
            encoder.writeObject(" ");
        } catch (IOException e) {
            e.printStackTrace(); // Trate a exceção conforme necessário
        }
    }
}
