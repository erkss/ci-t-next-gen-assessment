public class SevenSegmentify {

    // Mapeamento de cada dígito de um display de 7 segmentos
    private static final String[][] digitos = {
            {" _ ", "| |", "|_|"},  // 0
            {"   ", "  |", "  |"},  // 1
            {" _ ", " _|", "|_ "},  // 2
            {" _ ", " _|", " _|"},  // 3
            {"   ", "|_|", "  |"},  // 4
            {" _ ", "|_ ", " _|"},  // 5
            {" _ ", "|_ ", "|_|"},  // 6
            {" _ ", "  |", "  |"},  // 7
            {" _ ", "|_|", "|_|"},  // 8
            {" _ ", "|_|", " _|"}   // 9
    };

    // Representação do separador ':'
    private static final String[] separador = {"   ", " . ", " . "};

    // Função que converte o horário no formato "HH:MM" para o formato de um display de 7 segmentos
    public static String sevenSegmentify(String time) {
        // Divide o parâmetro "time" no formato "HH:MM" em duas partes: horas e minutos
        String[] partes = time.split(":");
        String horas = partes[0];
        String minutos = partes[1];

        // Inicializa as linhas que vão representar o display
        StringBuilder linha1 = new StringBuilder();
        StringBuilder linha2 = new StringBuilder();
        StringBuilder linha3 = new StringBuilder();

        // Construção dos segmentos para horas, separador ':' e minutos
        for (int i = 0; i < (horas + ":" + minutos).length(); i++) {
            char digito = (horas + ":" + minutos).charAt(i);

            if (digito == ':') {
                // Adiciona a representação do ':'
                linha1.append(separador[0]);
                linha2.append(separador[1]);
                linha3.append(separador[2]);
            } else {
                // Converte o caractere para seu valor numérico
                int numero = Character.getNumericValue(digito);

                // Adiciona o espaço vazio para representar o zero apagado a esquerda
                if (numero == 0 && i == 0 && horas.length() == 2) {
                    linha1.append("   "); // Espaço na parte superior
                    linha2.append("   "); // Espaço na parte intermediária
                    linha3.append("   "); // Espaço na parte inferior
                } else {
                    // Adiciona a representação do dígito ao display
                    linha1.append(digitos[numero][0]);
                    linha2.append(digitos[numero][1]);
                    linha3.append(digitos[numero][2]);
                }
            }
        }

        // Retorna todas as linhas com o formato de um display de 7 segmentos
        return linha1 + "\n" + linha2 + "\n" + linha3;

    }

    public static void main(String[] args) {
        // Teste para o caso com zero à esquerda
        System.out.println(sevenSegmentify("06:03"));

        // Teste para o caso sem zero à esquerda
        System.out.println(sevenSegmentify("13:24"));
    }
}