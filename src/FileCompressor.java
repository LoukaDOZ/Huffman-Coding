import huffman.*;
import io.BinaryReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Gère la compression/décompression
 * Contient surtout le main
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class FileCompressor {
    /**
     * Commande pour compresser
     */
    private final static String COMPRESS_COMMAND = "compress";

    /**
     * Commande pour décompresser
     */
    private final static String DECOMPRESS_COMMAND = "decompress";

    /**
     * Commande pour quitter
     */
    private final static String QUIT_COMMAND = "//quit";

    /**
     * Compresse un fichier
     * @param source fichier à compresser
     * @param destination fichier qui reçoit la compression
     */
    public static void compress(String source, String destination) {
        ArrayList<TreeElement> heap = null;

        try {
            //Récupère les caractère et leur fréquence
            heap = new FrequencyReader().readFile(source);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Construit l'arbre
        HuffmanTree tree = new HuffmanTree(new ArrayList<>(heap));
        char[] abc = new char[3];
        abc[0] = 'A';
        abc[1] = 'B';
        abc[2] = 'C';

        //Affiche le code de A B et C
        for(char c : abc) {
            String code =  tree.getCharCode(c);

            if(code.equals(""))
                code = "Non présent dans l'arbre";

            System.out.println("Code de '" + c + "' = " + code);
        }

        //Compresse le fichier
        tree.compress(source, destination);
    }

    /**
     * Décompresse un fichier
     * @param source fichier compressé
     * @param destination fichier recevant le message décompressé
     */
    public static void decompress(String source, String destination) {
        if(!source.endsWith(".huf"))
            throw new IllegalArgumentException("File " + source + " should end with the .huf suffix");

        HuffmanTree tree = new HuffmanTree();
        BinaryReader br = new BinaryReader(source);

        //Décode l'abre
        Node node = (Node) HuffmanTree.decodeTree(br);
        tree.setTree(node);
        //Décompresse le fichier
        tree.decompress(br, destination);

        br.close();
    }

    /**
     * Lancer une compression et décompression
     * @param args le premier argument doit être le chemin vers le fichier à compresser,
     *             le second doit être un chemin vers le dossier qui va receptionner le fichier décompressé
     * @throws IllegalArgumentException s'il manque un argument, si le premier n'existe pas ou n'est pas un fichier,
     *                                  si le second n'existe pas ou n'est pas un dossier
     */
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        File source;
        File destination;

        try {
            while(!answer.equals(QUIT_COMMAND)) {
                System.out.println("Pour compresser un fichier, entrez : " + COMPRESS_COMMAND);
                System.out.println("Pour décompresser un fichier, entrez : " + DECOMPRESS_COMMAND);
                System.out.println("Pour quitter, entrez : " + QUIT_COMMAND);
                answer = br.readLine();

                //Demande de compression et décompression
                if(answer.equals(COMPRESS_COMMAND) || answer.equals(DECOMPRESS_COMMAND)) {
                    //Récupération du fichier source
                    System.out.println("Entrez le chemin vers le fichier à " + (answer.equals(COMPRESS_COMMAND) ? "compresser" : "décompresser"));
                    String file = br.readLine();
                    source = new File(file);

                    //Quitter
                    if(file.equals(QUIT_COMMAND)) {
                        break;
                    } else if(!source.exists() || !source.isFile()) {   //Le fichier n'existe pas ou n'est pas un fichier
                        System.out.println(source.getAbsolutePath() + " n'existe pas ou n'est pas un fichier");
                    } else {
                        //Récupération du dossier de destination
                        System.out.println("Entrez le chemin vers le dossier où placer le fichier " +
                                    (answer.equals(COMPRESS_COMMAND) ? "compressé" : "décompressé"));

                        file = br.readLine();
                        destination = new File(file);

                        //Quitter
                        if(file.equals(QUIT_COMMAND)) {
                            break;
                        } else if(!destination.exists() || !destination.isDirectory()) {    //Le dossier n'existe pas ou n'est pas un dossier
                            System.out.println(source.getAbsolutePath() + " n'existe pas ou n'est pas un dossier");
                        } else if(answer.equals(COMPRESS_COMMAND)) {    //Compression du fichier source dans destination/source.huf
                            System.out.println("////// Compression ///////");

                            File compressedFile = new File(destination.getAbsolutePath(), source.getName() + ".huf");
                            String compressedPath = compressedFile.getAbsolutePath();

                            FileCompressor.compress(source.getAbsolutePath(), compressedPath);

                            float rate = ((float) compressedFile.length()) / ((float) source.length());

                            System.out.println("Fichier compressé : " + compressedFile.getAbsolutePath());
                            System.out.println("Taux de compression = " +
                                    compressedFile.length() + " / " + source.length() + " = " + rate + " (" + rate * 100 + " %)");

                            System.out.println();
                        } else {    //Décompression du fichier source dans destination/dec_source
                            System.out.println("/////// Décompression ///////");

                            String decompressedPath = new File(
                                    destination.getAbsolutePath(),
                                    "dec_" + source.getName().substring(0, source.getName().length() - 4)
                            ).getAbsolutePath();

                            FileCompressor.decompress(source.getAbsolutePath(), decompressedPath);
                            System.out.println("Fichier décompressé : " + decompressedPath);
                            System.out.println();
                        }
                    }
                }
            }
        } catch (IOException e) {   //Erreur de lecture
            System.out.println("Error while reading stdin");
            e.printStackTrace();
        }

        //Fermer le BuffuredReader
        try {
            br.close();
        } catch (IOException e) {
            System.out.println("Error while closing stdin");
            e.printStackTrace();
        }
    }
}
