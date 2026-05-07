OUT=out
MAIN=src.main.view.Main
SOURCES=$(shell find src -name "*.java")
JUNIT_JAR=lib/junit-platform-console-standalone.jar

.PHONY: all compile run clean rebuild

all: compile

compile:
	@mkdir -p $(OUT)
	@javac -d $(OUT) -cp ".:$(JUNIT_JAR)" $(SOURCES)

run: compile
	@java -cp "$(OUT):." $(MAIN)

test: compile
	@java -jar $(JUNIT_JAR) --class-path $(OUT) --scan-class-path

clean:
	@rm -rf $(OUT)

rebuild: clean run
