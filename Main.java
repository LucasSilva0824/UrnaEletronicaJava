import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Candidato {
    private String nome;
    private int numero;
    private int votos;

    public Candidato(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
        this.votos = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public int getVotos() {
        return votos;
    }

    public void incrementarVotos() {
        votos++;
    }
}

class UrnaEletronicaJava {
    private List<Candidato> candidatos;
    private int votosNulos;

    public UrnaEletronicaJava() {
        candidatos = new ArrayList<>();
        votosNulos = 0;
        inicializarCandidatos();
    }

    private void inicializarCandidatos() {
        candidatos.add(new Candidato("Ada Lovelace", 1));
        candidatos.add(new Candidato("Alan Turing", 2));
        candidatos.add(new Candidato("Marie Curie", 3));
        candidatos.add(new Candidato("Albert Einstein", 4));
        candidatos.add(new Candidato("Ludwig van Beethoven", 5));
    }

    public void receberVoto(String entrada) {
        if (entrada.matches("\\d{2}")) {
            int numero = Integer.parseInt(entrada);
            boolean votoValido = false;
            for (Candidato candidato : candidatos) {
                if (candidato.getNumero() == numero) {
                    candidato.incrementarVotos();
                    votoValido = true;
                    break;
                }
            }
            if (!votoValido) {
                votosNulos++;
            }
        } else {
            System.out.println("Entrada inválida. Por favor, digite um número entre 01 e 05.");
        }
    }

    public void apurarResultados() {
        System.out.println("\nResultado da Votação:");
        int totalVotosValidos = candidatos.stream().mapToInt(Candidato::getVotos).sum();

        for (Candidato candidato : candidatos) {
            double percentual = totalVotosValidos > 0 ? (candidato.getVotos() * 100.0) / totalVotosValidos : 0.0;
            System.out.printf("%s: %d votos (%.1f%%)%n", candidato.getNome(), candidato.getVotos(), percentual);
        }

        System.out.println("Votos Nulos: " + votosNulos);

        determinarVencedor();
    }

    private void determinarVencedor() {
        Candidato vencedor = null;
        boolean empate = false;

        for (Candidato candidato : candidatos) {
            if (vencedor == null || candidato.getVotos() > vencedor.getVotos()) {
                vencedor = candidato;
                empate = false;
            } else if (candidato.getVotos() == vencedor.getVotos()) {
                empate = true;
            }
        }

        if (vencedor != null && !empate) {
            System.out.println("Vencedor: " + vencedor.getNome());
        } else {
            System.out.println("Houve um empate.");
        }
    }

    public void exibirCandidatos() {
        System.out.println("Bem-vindo à Urna Eletrônica!");
        System.out.println("Candidatos:");
        for (Candidato candidato : candidatos) {
            System.out.printf("%02d - %s%n", candidato.getNumero(), candidato.getNome());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        UrnaEletronicaJava urna = new UrnaEletronicaJava();
        Scanner scanner = new Scanner(System.in);

        urna.exibirCandidatos();

        System.out.println("\nDigite o número do seu candidato (10 votos no total):");
        int votosRecebidos = 0;

        while (votosRecebidos < 10) {
            System.out.print("Voto " + (votosRecebidos + 1) + ": ");
            String entrada = scanner.nextLine();
            if (entrada.matches("\\d{2}")) {
                urna.receberVoto(entrada);
                votosRecebidos++;
            } else {
                System.out.println("Entrada inválida. Por favor, digite um número de dois dígitos (01, 02, 03, 04, 05).");
            }
        }

        urna.apurarResultados();
        scanner.close();
    }
}