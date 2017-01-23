
.PHONY : doc 

all :  doc cleanSemi

doc : 
	pdflatex *.tex && pdflatex *.tex

cleanSemi : 
	rm -f *.synctex.gz
	rm -f *.aux
	rm -f *.toc
	rm -f *.log
	
clean : 
	rm -f *.synctex.gz
	rm -f *.aux
	rm -f *.pdf
	rm -f *.toc
	rm -f *.log
	