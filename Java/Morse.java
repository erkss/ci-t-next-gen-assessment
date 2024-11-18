import java.util.ArrayList;
import java.util.List;

class Morse {
    // Representa os níveis da árvore binária do código Morse
    private static final String[][] morseTree = {
            {"E", "T"},
            {"I", "A", "N", "M"},
            {"S", "U", "R", "W", "D", "K", "G", "O"}
    };

    // Recebe uma sequência de sinais em Código Morse e retorna todas as letras possíveis
    public static List<String> possibilities(String signals) {
        List<String> results = new ArrayList<>();
        explorePossibilities(0, signals, 0, results);
        return results;
    }

    // Função para explorar as possibilidades dentro da árvore binária do Código Morse
    private static void explorePossibilities(int depth, String signals, int index, List<String> results) {
        if (depth == signals.length()) {
            results.add(morseTree[depth - 1][index]);
            return;
        }

        char signal = signals.charAt(depth);

        if (signal == '.') {
            explorePossibilities(depth + 1, signals, index * 2, results); // Caminha para o filho da esquerda
        } else if (signal == '-') {
            explorePossibilities(depth + 1, signals, index * 2 + 1, results); // Caminha para o filho da direita
        } else if (signal == '?') {
            // Caminha para ambas as possibilidades, filho da esquerda e direita
            explorePossibilities(depth + 1, signals, index * 2, results);
            explorePossibilities(depth + 1, signals, index * 2 + 1, results);
        }
    }

    public static void main(String[] args) {
        // Teste
        System.out.println(possibilities("."));
        System.out.println(possibilities("-"));
        System.out.println(possibilities("-."));
        System.out.println(possibilities("..."));
        System.out.println(possibilities("..-"));
        System.out.println(possibilities("?"));
        System.out.println(possibilities(".?"));
        System.out.println(possibilities("?-?"));
    }
}