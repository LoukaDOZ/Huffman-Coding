package huffman;

import utils.Utils;

/**
 * Noeud de l'arbre de Huffman
 * Contient deux enfants étant des {@link TreeElement}
 * @see TreeElement
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public class Node extends TreeElement {
    /**
     * Enfant de gauche, le plus petit
     */
    private TreeElement leftChild;
    /**
     * Enfant de droite, le plus grand
     */
    private TreeElement rightChild;

    /**
     * Constructeur
     * @param c valeur, doit être celle du plus grand enfant, donc celui de droite
     * @param frequency fréquence, doit être la somme des fréquence des enfants
     * @param leftChild enfant de gauche, doit être le plus petit
     * @param rightChild enfant de droite, doit être le plus grand
     * @throws NullPointerException si un des enfants ou les deux valent null
     * @throws IllegalArgumentException si la fréquence n'est pas la somme des fréquences des enfants
     * @throws IllegalArgumentException si la valeur n'est pas celle de l'enfant de droite
     */
    public Node(char c, int frequency, TreeElement leftChild, TreeElement rightChild) {
        super(c, frequency);

        if(leftChild == null || rightChild == null)
            throw new IllegalArgumentException("Children cannot be null");

        if(frequency != leftChild.getFrequency() + rightChild.getFrequency())
            throw new IllegalArgumentException("frequency must be the sum of children's frequency");

        if(c != rightChild.getValue())
            throw new IllegalArgumentException("c must be the value of the right child");

        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Obtenir l'enfant de gauche
     * @return l'enfant de gauche
     */
    public TreeElement getLeftChild() {
        return this.leftChild;
    }

    /**
     * Obtenir l'enfant de droite
     * @return l'enfant de droite
     */
    public TreeElement getRightChild() {
        return this.rightChild;
    }

    /**
     * Retourne la valeur binaire d'un caractère
     * @param c caractère
     * @return une chaine de 0 et 1 correspondant à la valeur binaire de c si le caractère est présent, sinon une chaine vide
     * @throws NullPointerException pour tout noeud n'ayant pas 2 enfants
     */
    @Override
    public String getCharCode(char c) {
        //Caractère trouvé
        if(this.leftChild.getValue() == c)
            return "0" + this.leftChild.getCharCode(c);
        //Sinon, fouiller le reste de l'arborescence en partant de l'enfant de gauche
        else {
            String code = this.leftChild.getCharCode(c);

            if(!code.equals(""))
                return "0" + code;
        }

        //Caractère trouvé
        if(this.rightChild.getValue() == c)
            return "1" + this.rightChild.getCharCode(c);
            //Sinon, fouiller le reste de l'arborescence en partant de l'enfant de droite
        else {
            String code = this.rightChild.getCharCode(c);

            if(!code.equals(""))
                return "1" + code;
        }

        //Le caractère n'est pas présent dans l'arborescence sous-jacente à ce noeud
        return "";
    }

    /**
     * Converti en chaite de caractères
     * @return le noeud sous forme de chaîne de cractères
     */
    @Override
    public String toString() {
        return "{ f = " + this.getFrequency() + ", v = '" + Utils.literalChar(this.getValue())
                + "', L = " + this.leftChild + ", R = " + this.rightChild + " }";
    }
}
