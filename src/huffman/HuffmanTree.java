package huffman;

import io.BinaryReader;
import io.BinaryWriter;
import io.ReadFile;
import io.WriteFile;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Gère l'arbre de Huffman
 *
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class HuffmanTree {
    /**
     * Racine
     */
    private Node root;


    /**
     * Constructeur
     * Construit un arbre vide
     */
    public HuffmanTree() {
        this.root = null;
    }

    /**
     * Constructeur
     *
     * @param root racine, utilise {@link HuffmanTree#setTree(Node)}
     **/
    public HuffmanTree(Node root) {
        this.setTree(root);
    }

    /**
     * Constructeur
     *
     * @param heap tas, utilise {@link HuffmanTree#setTree(ArrayList)}. Le tas sera modifiée, donner une copie pour éviter cela
     **/
    public HuffmanTree(ArrayList<TreeElement> heap) {
        this.setTree(heap);
    }

    /**
     * Définir l'arbre
     *
     * @param root racine du nouvel arbre
     * @throws NullPointerException si root vaut null
     **/
    public void setTree(Node root) {
        if (root == null)
            throw new NullPointerException("Root cannot be null");

        this.root = root;
    }

    /**
     * Trouve et supprime le plus petit {@link TreeElement} de le tas, selon la comparaison {@link TreeElement#comparedTo}
     *
     * @param heap tas
     * @return le plus petit élément
     */
    private TreeElement getSmaller(ArrayList<TreeElement> heap) {
        TreeElement min = heap.get(0);

        for (int i = 1; i < heap.size(); i++) {
            TreeElement te = heap.get(i);

            if (te.comparedTo(min) < 0)
                min = te;
        }

        heap.remove(min);
        return min;
    }

    /**
     * Contruit un arbre depuis un tas de {@link TreeElement}
     * cf. Algorithme de construction de l’arbre de Huffman
     *
     * @param heap tas. Le tas sera modifiée, donner une copie pour éviter cela
     * @throws NullPointerException     si le tas est null
     * @throws IllegalArgumentException si le tas contient 1 élément ou moins
     **/
    public void setTree(ArrayList<TreeElement> heap) {
        int n = heap.size();

        if (n <= 1)
            throw new IllegalArgumentException("The heap must contain more than one element");

        new FrequencyReader().heapSort(heap);
        for (int i = 1; i < n; i++) {
            TreeElement x = this.getSmaller(heap);
            TreeElement y = this.getSmaller(heap);

            Node z = new Node(y.getValue(), x.getFrequency() + y.getFrequency(), x, y);

            heap.add(z);
        }

        this.root = (Node) heap.get(0);
    }

    /**
     * Retourne la valeur binaire d'un caractère
     *
     * @param c caractère
     * @return une chaine de 0 et 1 correspondant à la valeur binaire de c,
     * sinon une chaine vide si le caractère n'est pas présent dans l'arbre
     * @throws NullPointerException pour tout noeud n'ayant pas 2 enfants ou si l'arbre est vide
     */
    public String getCharCode(char c) {
        return this.root.getCharCode(c);
    }

    /**
     * Encode l'arbre en une chaîne de bit à partir du noeud actuel, afin de pouvoir le reconstruire lors du décompressage
     * 0 représente un noeud, 1 une feuille
     * Parcours en profondeur l'abre en priorisant l'enfant de gauche
     * Chaque feuille donne 1 suivit de l'octet correspondant à sa valeur : 1xxxxxxxx
     * Ainsi, 001xxxxxxxxxxxxxxxx1yyyyyyyyyyyyyyyy1zzzzzzzzzzzzzzzz correspond à une arbre :
     *
     * Noeud (racine)
     * \_gauche_ noeud
     * .....\_gauche_ feuille (valeur en binaire = xxxxxxxxxxxxxxxx)
     * .....\_droite_ feuille (valeur en binaire = yyyyyyyyyyyyyyyy)
     * \_droite_ feuille (valeur en binaire = zzzzzzzzzzzzzzzz)     *
     *
     * @param bw   écrivain
     * @param node noeud actuel
     */
    private void encodeTree(BinaryWriter bw, Node node) {
        TreeElement[] children = new TreeElement[2];
        children[0] = node.getLeftChild(); //Enfant gauche
        children[1] = node.getRightChild(); //Enfant droit
        bw.write("0"); //Ajouter 0 car noeud

        for (TreeElement child : children) {
            if (child instanceof Node)
                this.encodeTree(bw, (Node) child);
            else {
                //Ajouter 1 suivit de la valeur de la feuille en binaire
                //On doit s'assurer, à la décompression, de bien récupérer les 2 octets correpondant à la valeur (caractère)
                //de la feuille
                //Pour cela, on ajoute autant de 0 que nécessaire pour être sûr d'avoir une suite de 16 bits exactement
                StringBuilder binaryValue = new StringBuilder(Utils.charToBinaryString(child.getValue()));
                Utils.completeBinaryStringBefore16(binaryValue);
                bw.write("1");
                bw.write(binaryValue.toString());
            }
        }
    }

    /**
     * Compresse un contenu dans un fichier, à l'aide le l'arbre de Huffman et à l'aide de {@link BinaryWriter}
     *
     * @param source      chemin vers le fichier à compresser
     * @param destination chemin vers le fichier qui va recevoir le contenu encodé
     * @throws IllegalStateException         si l'arbre vaut null
     * @throws UnsupportedOperationException si le contenu à compresser contient un caractère unconnu de l'arbre
     */
    public void compress(String source, String destination) {
        if (this.root == null)
            throw new IllegalStateException("Tree is null");

        ReadFile rf = new ReadFile(source);
        BinaryWriter bw = new BinaryWriter(destination);
        Character c;

        //Encoder l'arbre
        this.encodeTree(bw, this.root);

        //Convertir chaque caractère en une suite de bit basé sur l'arbre et construire une chaîne avec
        //Cette map stocke le code des caractères déjà rencontrés dans un but d'optimisation
        Map<Character, String> codes = new HashMap<>();
        while ((c = rf.readChar()) != null) {
            if (!codes.containsKey(c)) {
                String code = this.getCharCode(c);

                //Vérifier que le caractère existe dans l'arbre
                if (!code.equals(""))
                    codes.put(c, code);
                else
                    throw new UnsupportedOperationException("Tree does not contain the value " + c);
            }

            bw.write(codes.get(c));
        }

        String EOFCode = this.getCharCode(FrequencyReader.EOF);
        if (!EOFCode.equals(""))
            //Ajouter un EOF à la fin
            bw.write(EOFCode);
        else
            throw new UnsupportedOperationException("Tree does not contain the value EOF");

        //A la fin du fichier encodé, des 0 sont rajoutés si l'ensemble des bits écrits ne sont pas multiples de 8,
        //cela permet d'écrire un octet.
        //Les 0 rajoutés ne seront pas pris en compte puisqu'ils sont après le code de EOF.
        //Ils permettent d'avoir une chaîne multiple de 8 et ainsi être sûr d'avoir la bonne séquence de bit à la décompression.
        //En effet, transformer 101 en octet donne 00000101, ce qui change le contenu.
        //Alors que si 101 est ou fait parti du codage de EOF, 10100000 ne change rien, les 5 deniers 0 étant ignorés
        bw.close();
    }

    /**
     * Décode l'arbre
     * @param br lecteur du fichier compressé
     * @return la racine de l'arbre
     */
    public static TreeElement decodeTree(BinaryReader br) {
        if (br.readBit()) {
            //Leaf
            char v = Utils.binaryStringToChar(br.readChar());

            return new Leaf(v);
        } else {
            //Node
            TreeElement left = HuffmanTree.decodeTree(br);
            TreeElement right = HuffmanTree.decodeTree(br);

            return new Node(right.getValue(), left.getFrequency() + right.getFrequency(), left, right);
        }
    }

    /**
     * Décompresse le contenu d'un fichier compressé, à l'aide de {@link BinaryReader}
     *
     * @param br          écrivain
     * @param destination chemin vers le fichier qui va recevoir le contenu décompressé
     * @throws IllegalStateException si l'arbre vaut null
     */
    public void decompress(BinaryReader br, String destination) {
        if (this.root == null)
            throw new IllegalStateException("Tree is null");

        WriteFile wf = new WriteFile(destination);
        TreeElement current = this.root;

        while (true) {
            if (!br.readBit())
                current = ((Node) current).getLeftChild();
            else
                current = ((Node) current).getRightChild();

            if (current instanceof Leaf) {
                if (current.getValue() == FrequencyReader.EOF)
                    break;
                else {
                    wf.write(current.getValue());
                    current = this.root;
                }
            }
        }

        wf.close();
    }

    /**
     * Retroune l'arbre sous forme de chaîne de caractères
     *
     * @return l'arbre sous forme de chaîne de caractères
     */
    @Override
    public String toString() {
        if (this.root == null)
            return "{}";

        return this.root.toString();
    }
}