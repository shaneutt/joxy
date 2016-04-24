GRADLE    := gradle
SCRIPTDIR := `pwd`/script
LIBDIR    := `pwd`/build/libs
BINDIR    := `pwd`/build/bin

all: test bin

bin:
	@$(GRADLE) jar
	@rm -f $(SCRIPTDIR)/joxy
	@cat $(SCRIPTDIR)/joxy.script.template > $(SCRIPTDIR)/joxy.sh
	@uuencode $(LIBDIR)/joxy.jar joxy.jar >> $(SCRIPTDIR)/joxy.sh
	@chmod +x $(SCRIPTDIR)/joxy.sh
	@mkdir -p $(BINDIR)
	@mv $(SCRIPTDIR)/joxy.sh $(BINDIR)/joxy
	@echo "\njoxy built at $(BINDIR)/joxy!"

clean:
	@$(GRADLE) clean

compile:
	@$(GRADLE) build

jar:
	@$(GRADLE) jar

run: bin
	@echo ''
	@./build/bin/joxy

test:
	@$(GRADLE) test

.PHONY: bin clean compile jar run test
