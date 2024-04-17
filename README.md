# Huffman Coding

File compression/decompression using Huffman coding.

- [Source code](src/)
- [Project report (fr)](pdf/rapport_huffman.pdf)

## Usage

| Command            | Description          |
|--------------------|----------------------|
| `make all`         | Build project        |
| `make build`       | Build Java files     |
| `make jar`         | Generate JAR         |
| `make run`         | Run project          |
| `make clean`       | Clean project        |
| `make clean-build` | Clean compiled files |
| `make clean-jar`   | Clean generated JAR  |

## Example
### Start project

```bash
make
make run
```

### Compress a file

Compressing [`original/example_1.2Ko.txt`](original/example_1.2Ko.txt) into `compressed/example_1.2Ko.txt.huf`:
```
Pour compresser un fichier, entrez : compress
Pour décompresser un fichier, entrez : decompress
Pour quitter, entrez : //quit
compress
Entrez le chemin vers le fichier à compresser
original/example_1.2Ko.txt
Entrez le chemin vers le dossier où placer le fichier compressé
compressed/
////// Compression ///////
Code de 'A' = 00001
Code de 'B' = 11010010
Code de 'C' = 1110000
Fichier compressé : /home/me/Bureau/Huffman-Coding/compressed/example_1.2Ko.txt.huf
Taux de compression = 894 / 1166 = 0.7667239 (76.672386 %)
```

### Decompress a file

Decompressing `compressed/example_1.2Ko.txt.huf` into `decompressed/dec_example_1.2Ko.txt`:
```
Pour compresser un fichier, entrez : compress
Pour décompresser un fichier, entrez : decompress
Pour quitter, entrez : //quit
decompress
Entrez le chemin vers le fichier à décompresser
compressed/example_1.2Ko.txt.huf
Entrez le chemin vers le dossier où placer le fichier décompressé
decompressed/
/////// Décompression ///////
Fichier décompressé : /home/me/Bureau/Huffman-Coding/decompressed/dec_example_1.2Ko.txt
```

### Quitting

```
Pour compresser un fichier, entrez : compress
Pour décompresser un fichier, entrez : decompress
Pour quitter, entrez : //quit
//quit
```