package huffman;

import java.util.Objects;

/**
 * Elément queconque de l'arbre de Huffman (noeud ou feuille)
 *
 * @author louka DOZ, Wissam AIT KHEDDACHE
 */
public abstract class TreeElement {
    /**
     * Fréquence
     */
    private int frequency;

    /**
     * Valeur
     */
    private char value;

    /**
     * Fréquence par défaut
     */
    public final static int UNSET_FREQUENCY = 0;

    /**
     * Constructeur
     * Fréquence mise à 0
     *
     * @param value valeur
     */
    public TreeElement(char value) {
        this.value = value;
        this.frequency = UNSET_FREQUENCY;
    }

    /**
     * Constructeur
     *
     * @param value     valeur
     * @param frequency fréquence
     */
    public TreeElement(char value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    /**
     * Obtenir la valeur
     *
     * @return caractère/valeur de l'élément
     */
    public char getValue() {
        return this.value;
    }

    /**
     * Est-ce que la fréquence a été définie
     *
     * @return true si la fréquence est > 0, false sinon
     */
    public boolean isFrequencySet() {
        return (this.frequency > UNSET_FREQUENCY);
    }

    /**
     * Définir la fréquence
     *
     * @param frequency nouvelle fréquence
     */
    public void setFrequency(int frequency) {
        if (frequency < 0)
            throw new IllegalArgumentException("Frequency must be superior or equal to 0");

        this.frequency = frequency;
    }

    /**
     * Obtenir la fréquence
     *
     * @return la fréquence
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Retourne la valeur binaire d'un caractère
     *
     * @param c caractère
     * @return une chaine de 0 et 1 correspondant à la valeur binaire de c si le caractère est présent, sinon une chaine vide
     * @throws NullPointerException pour tout noeud n'ayant pas 2 enfants
     */
    public abstract String getCharCode(char c);

    /**
     * Compare deux éléments selon
     * Les fréquences sont d'abord comparées, si elles sont égales, les valeurs sont comparées
     *
     * @param t élément à comparer
     * @return 0 si les éléments sont égaux, < 0 si t est supérieur, > 0 si t est inférieur
     * @throws NullPointerException si t est null
     */
    public int comparedTo(TreeElement t) {
        if (t == null)
            throw new NullPointerException("Argument can't be null");

        if (this.frequency != t.getFrequency())
            return this.frequency - t.getFrequency();

        return this.value - t.getValue();
    }

    /**
     * Vérifie si deux éléments sont similaires
     *
     * @param o élément à comparer
     * @return true si les éléments sont les mêmes, s'ils sont de la même classe ou si leur valeur et fréquence sont égales, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || o.getClass() != this.getClass())
            return false;

        TreeElement t = (TreeElement) o;

        return (t.getValue() == this.value && t.getFrequency() == this.frequency);
    }

    /**
     * Obtenir le hash code de l'élément
     *
     * @return le hash code de l'élément
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.frequency, this.value);
    }
}
