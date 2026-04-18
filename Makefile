OUT=out
MAIN=src.view.Main
SOURCES=$(shell find src -name "*.java")

.PHONY: all compile run clean rebuild

all: compile

compile:
	@mkdir -p $(OUT)
	@javac -d $(OUT) $(SOURCES)

run: compile
	@java -cp $(OUT) $(MAIN)

clean:
	@rm -rf $(OUT)

rebuild: clean run
