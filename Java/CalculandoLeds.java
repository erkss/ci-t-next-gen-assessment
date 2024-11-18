public class CalculandoLeds {

    // Função criada para calcular o número total de LEDs
    public static Integer calculaTotalLeds(Integer altura, Integer largura) {
        // Se altura ou largura for 0, retorna 0 LEDs
        if (altura == 0 || largura == 0) {
            return 0;
        }
        return (altura + 1) * (largura + 1);
    }

    // Exemplo
    public static void main(String[] args) {
        // Exibe o resultado utilizando a função criada
        System.out.println(calculaTotalLeds(2, 4));
    }
}