import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class QuickSort {

    static final Random RND = new Random(2026);

    static void troca(int[] a, int x, int y) {
        int t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    // QuickSort - Particionamento de Lomuto (com opção de pivô aleatório)
    static void lomuto(int[] a, boolean aleatorio) {
        Deque<int[]> pilha = new ArrayDeque<>();
        pilha.push(new int[]{0, a.length - 1});
        while (!pilha.isEmpty()) {
            int[] f = pilha.pop();
            int lo = f[0], hi = f[1];
            if (lo >= hi) continue;
            if (aleatorio) troca(a, lo + RND.nextInt(hi - lo + 1), hi);
            int p = a[hi], i = lo - 1;
            for (int j = lo; j < hi; j++) {
                if (a[j] <= p) troca(a, ++i, j);
            }
            troca(a, i + 1, hi);
            pilha.push(new int[]{lo, i});
            pilha.push(new int[]{i + 2, hi});
        }
    }

    // Leitura dos arquivos .txt no mesmo diretório
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

    public static void main(String[] args) {
        String[] arquivos = {"1000_numbers.txt", "5000_numbers.txt", "10000_numbers.txt"};

        System.out.println("=== INICIANDO BENCHMARK DO QUICK SORT ===");

        for (String nomeArquivo : arquivos) {
            try {
                int[] vetor = carregarVetorDoArquivo(nomeArquivo);

                long tempoInicial = System.nanoTime();

                // Executa QuickSort (Lomuto com pivô aleatório para evitar pior caso)
                lomuto(vetor, true);

                long tempoFinal = System.nanoTime();

                double tempoExecucaoMs = (tempoFinal - tempoInicial) / 1000000.0;

                System.out.printf("Arquivo: %-15s | Tamanho: %-6d elementos | Tempo: %.4f ms\n", 
                                  nomeArquivo, vetor.length, tempoExecucaoMs);

            } catch (IOException e) {
                System.out.println("Erro ao carregar o arquivo '" + nomeArquivo + "': " + e.getMessage());
            }
        }
    }
}

