import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BubbleSort {

    // 1. ALGORITMO BUBBLE SORT (A tua lógica original otimizada)
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean troca;
        for (int i = 0; i < n - 1; i++) {
            troca = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    troca = true;
                }
            }
            if (!troca) {
                break;
            }
        }
    }

    // 2. MÉTODO PARA LER O ARQUIVO TXT (Herdado do teu código de Merge Sort)
    public static int[] carregarVetorDoArquivo(String caminhoArquivo) throws IOException {
        List<Integer> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    lista.add(Integer.parseInt(linha));
                }
            }
        }
        return lista.stream().mapToInt(val -> val).toArray();
    }

    // 3. EXECUÇÃO PRINCIPAL (Estrutura de Benchmark)
    public static void main(String[] args) {
        // Nomes dos arquivos que já estão no teu Termux
        String[] arquivos = {"1000_numbers.txt", "5000_numbers.txt", "10000_numbers.txt"};

        System.out.println("=== INICIANDO BENCHMARK DO BUBBLE SORT ===");

        for (String nomeArquivo : arquivos) {
            try {
                // Carrega os dados do arquivo
                int[] vetor = carregarVetorDoArquivo(nomeArquivo);

                // Marca o início
                long tempoInicial = System.nanoTime();

                // Executa a tua lógica de ordenação
                bubbleSort(vetor);

                // Marca o fim
                long tempoFinal = System.nanoTime();

                // Calcula o tempo
                double tempoExecucaoMs = (tempoFinal - tempoInicial) / 1000000.0;

                // Imprime a tabela de resultados
                System.out.printf("Arquivo: %-15s | Tamanho: %-6d elementos | Tempo: %.4f ms\n", 
                                  nomeArquivo, vetor.length, tempoExecucaoMs);

            } catch (IOException e) {
                System.out.println("Erro ao carregar o arquivo '" + nomeArquivo + "': " + e.getMessage());
            }
        }
    }
}
