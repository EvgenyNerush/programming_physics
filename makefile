TEX := pdflatex -shell-escape
OBJECTS := lectures.tex lectures.bib

.PHONY : lectures clean

lectures: $(OBJECTS)
	$(TEX) lectures.tex && bibtex lectures.aux && $(TEX) lectures.tex && $(TEX) lectures.tex lectures.pdf

%: %.cpp $(SRC)
	g++ $(FLAGS3) $< -o $@ && ./$@

clean:
	-rm -r lectures.log lectures.aux lecturesNotes.bib lectures.bbl lectures.blg _minted_lectures


