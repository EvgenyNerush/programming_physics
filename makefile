TEX := pdflatex -shell-escape
OBJECTS := lectures.tex lectures.bib

lectures: $(OBJECTS)
	$(TEX) lectures.tex && bibtex lectures.aux && $(TEX) lectures.tex && $(TEX) lectures.tex lectures.pdf

.PHONY : clean
clean:
	-rm lectures.log lectures.aux lecturesNotes.bib lectures.bbl lectures.blg


