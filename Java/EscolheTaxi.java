class EscolheTaxi {
    public static String escolheTaxi(String tf1, String vqr1, String tf2, String vqr2) {
        // Converte os valores de String para Double
        double taxa1 = Double.parseDouble(tf1);
        double valorPorKm1 = Double.parseDouble(vqr1);
        double taxa2 = Double.parseDouble(tf2);
        double valorPorKm2 = Double.parseDouble(vqr2);

        // Variável para retornar a resposta
        String resposta;

        // Verifica se o valor por km das duas empresas é igual. Se o valor por km é igual, a escolha depende da taxa fixa
        if (valorPorKm1 == valorPorKm2) {
            if (taxa1 < taxa2) {
                resposta = "Empresa 1";
            } else if (taxa2 < taxa1) {
                resposta = "Empresa 2";
            } else {
                resposta = "Tanto faz";
            }
        } else {
            // Calcula a distância onde os preços são iguais
            double distanciaEquilibrio = (taxa2 - taxa1) / (valorPorKm1 - valorPorKm2);

            if (distanciaEquilibrio < 0) {
                // Se a distância de equilíbrio for negativa, uma empresa é sempre mais barata
                resposta = valorPorKm1 < valorPorKm2 ? "Empresa 1" : "Empresa 2";
            } else {
                // Arredonda a distância de equilíbrio para duas casas decimais
                distanciaEquilibrio = Math.round(distanciaEquilibrio * 100.0) / 100.0;

                // Define o formato baseado na quantidade de casas decimais
                String formato = (distanciaEquilibrio == Math.floor(distanciaEquilibrio)) ? "%.1f" : "%.2f";

                // Define a empresa mais barata antes e depois da distância de equilíbrio
                double custoAntes = distanciaEquilibrio - 1;
                double custoDepois = distanciaEquilibrio + 1;

                String empresaAntesN = (taxa1 + valorPorKm1 * custoAntes) < (taxa2 + valorPorKm2 * custoAntes)
                        ? "Empresa 1" : "Empresa 2";

                String empresaDepoisN = (taxa1 + valorPorKm1 * custoDepois) < (taxa2 + valorPorKm2 * custoDepois)
                        ? "Empresa 1" : "Empresa 2";

                // Formata a resposta
                resposta = String.format(
                        "%s quando a distância < " + formato + ", Tanto faz quando a distância = " + formato + ", %s quando a distância > " + formato,
                        empresaAntesN, distanciaEquilibrio, distanciaEquilibrio, empresaDepoisN, distanciaEquilibrio
                );
            }
        }

        return resposta;
    }

    public static void main(String[] args) {
        // Teste
        String teste = EscolheTaxi.escolheTaxi("2.50", "1.00", "5.00", "0.75");
        System.out.println(teste);
    }
}