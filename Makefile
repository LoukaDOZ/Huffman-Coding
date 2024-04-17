CC=javac
FLAGS=
CLASSES=$(patsubst %.java,%.class,$(wildcard $(SrcFolder)/*.java)) $(patsubst %.java,%.class,$(wildcard $(SrcFolder)/huffman/*.java)) $(patsubst %.java,%.class,$(wildcard $(SrcFolder)/io/*.java)) $(patsubst %.java,%.class,$(wildcard $(SrcFolder)/utils/*.java))
SrcFolder=src
JarFolder=jar
Jar=HuffmanCompressor.jar
JarEntrypoint=FileCompressor

all: clean-jar build jar clean-build

build: $(CLASSES)

%.class: %.java
	cd $(SrcFolder) && $(CC) $(FLAGS) $(patsubst $(SrcFolder)/%.java,%.java,$<)

jar:
	mkdir $(JarFolder)
	cd $(SrcFolder) && jar cfe ../$(JarFolder)/$(Jar) $(JarEntrypoint) $(patsubst $(SrcFolder)/%.class,%.class,$(CLASSES))

run:
	java -jar $(JarFolder)/$(Jar)

clean: clean-build clean-jar

clean-build:
	rm -f $(CLASSES)

clean-jar:
	rm -rf $(JarFolder)