import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FindSnakeOnGrid {
    public static ArrayList<ArrayList<Integer>> findSnakeOnGrid(ArrayList<String> grid) {
        ArrayList<ArrayList<Integer>> snakeCoordinates = new ArrayList<>();
        int rows = grid.size();
        int cols = grid.get(0).length();

        // Encontrar a posição da cabeça da cobra ('h')
        int headRow = -1, headCol = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid.get(i).charAt(j) == 'h') {
                    headRow = i;
                    headCol = j;
                    break;
                }
            }
            if (headRow != -1) break; // Parar após encontrar a cabeça
        }

        // Adicionar a posição inicial da cabeça na lista de coordenadas
        if (headRow != -1) {
            snakeCoordinates.add(new ArrayList<>(Arrays.asList(headCol, headRow)));
        } else {
            // Se não encontrar a cabeça, retornar uma lista vazia
            return snakeCoordinates;
        }

        // Conjunto para armazenar posições visitadas
        Set<String> visited = new HashSet<>();
        visited.add(headRow + "," + headCol);

        // Seguir o corpo da cobra
        boolean foundNextSegment = true;
        while (foundNextSegment) {
            foundNextSegment = false;

            // Movimentos possíveis: direita, esquerda, cima, baixo
            int[][] directions = { {0, 1}, {0, -1}, {-1, 0}, {1, 0} };
            char[] oppositeChars = { '<', '>', 'v', '^' }; // Caractere oposto esperado em cada direção

            for (int i = 0; i < directions.length; i++) {
                int newRow = headRow + directions[i][0];
                int newCol = headCol + directions[i][1];

                // Verifica se a nova posição está dentro do grid e não foi visitada
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        !visited.contains(newRow + "," + newCol)) {

                    char segment = grid.get(newRow).charAt(newCol);

                    // Verifica se o caractere atual é parte do corpo e aponta corretamente para o segmento anterior
                    if (segment == oppositeChars[i]) {
                        headRow = newRow;  // Atualiza para o novo segmento
                        headCol = newCol;
                        snakeCoordinates.add(new ArrayList<>(Arrays.asList(headCol, headRow)));
                        visited.add(newRow + "," + newCol); // Marca como visitado
                        foundNextSegment = true;
                        break; // Encontra o próximo segmento e interrompe
                    }
                }
            }
        }

        // Retorna a lista completa de coordenadas da cobra, da cabeça até a cauda
        return snakeCoordinates;
    }

    public static void main(String[] args) {
        // Teste
        ArrayList<String> grid = new ArrayList<>(Arrays.asList(
                "v<<",
                "vh<",
                ">>^"
        ));

        ArrayList<ArrayList<Integer>> result = findSnakeOnGrid(grid);
        for (ArrayList<Integer> coordinates : result) {
            System.out.println(coordinates);
        }
    }
}