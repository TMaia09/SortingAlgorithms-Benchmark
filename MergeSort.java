import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    // 1. ALGORITMO MERGE SORT
    public static void mergeSort(int[] array, int inicio, int fim) {
        if (inicio < fim) {
            int meio = inicio + (fim - inicio) / 2;

            mergeSort(array, inicio, meio);
            mergeSort(array, meio + 1, fim);

            intercalar(array, inicio, meio, fim);
        }
    }

    private static void intercalar(int[] array, int inicio, int meio, int fim) {
        int n1 = meio - inicio + 1;
        int n2 = fim - meio;

        int[] esquerda = new int[n1];
        int[] direita = new int[n2];

        for (int i = 0; i < n1; i++) {
            esquerda[i] = array[inicio + i];
        }
        for (int j = 0; j < n2; j++) {
            direita[j] = array[meio + 1 + j];
        }

        int i = 0, j = 0;
        int k = inicio;

        while (i < n1 && j < n2) {
            if (esquerda[i] <= direita[j]) {
                array[k] = esquerda[i];
                i++;
            } else {
                array[k] = direita[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = esquerda[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = direita[j];
            j++;
            k++;
        }
    }

    // 2. MÉTODO PARA LER O ARQUIVO TXT
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
        // Converte a lista dinâmica para um array nativo int[]
        return lista.stream().mapToInt(val -> val).toArray();
    }

    // 3. EXECUÇÃO PRINCIPAL
    public static void main(String[] args) {
        // Nomes exatos dos 3 arquivos .txt
        String[] arquivos = {"1000_numbers.txt", "5000_numbers.txt", "10000_numbers.txt"};

        System.out.println("=== INICIANDO BENCHMARK DO MERGE SORT ===");

        for (String nomeArquivo : arquivos) {
            try {
                // Carrega o vetor para a memória ANTES da contagem
                int[] vetor = carregarVetorDoArquivo(nomeArquivo);

                // Marca o início do processo de ordenação
                long tempoInicial = System.nanoTime();

                // Executa o Merge Sort
                mergeSort(vetor, 0, vetor.length - 1);

                // Marca o fim do processo
                long tempoFinal = System.nanoTime();

                // Calcula a diferença e converte nanossegundos para milissegundos
                double tempoExecucaoMs = (tempoFinal - tempoInicial) / 1000000.0;

                System.out.printf("Arquivo: %-15s | Tamanho: %-6d elementos | Tempo: %.4f ms\n", 
                                  nomeArquivo, vetor.length, tempoExecucaoMs);

            } catch (IOException e) {
                System.out.println("Erro ao carregar o arquivo '" + nomeArquivo + "': " + e.getMessage());
            }
        }
    }
}
