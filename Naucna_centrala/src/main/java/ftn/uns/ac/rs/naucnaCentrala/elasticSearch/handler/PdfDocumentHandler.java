package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.AutorIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.model.Coauthor;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

import org.apache.lucene.document.Document;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
//import org.apache.pdfbox.io.RandomAccessFile;
//import org.apache.pdfbox.pdfparser.PDFParser;
//import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class PdfDocumentHandler extends DocumentHandler {

    @Override
    public PaperIndexUnit getIUPaper(Paper paper, Path filepath) {
        PaperIndexUnit paperIU = new PaperIndexUnit();

        paperIU.setFilename(paper.getFilename());
        paperIU.setNaslovRada(paper.getNaslovRada());
        File bookFile = filepath.toFile();
        paperIU.setText(getText(bookFile));

        if (paper.getApstract() != null) {
        	paperIU.setApstrakt(paper.getApstract());
        }
        if (paper.getKeywords() != null) {
        	paperIU.setKeywords(paper.getKeywords());
        }
        if (paper.getScientificField() != null) {
        	paperIU.setNaucnaOblast(paper.getScientificField().getScientificFieldName().name());
        }
        if (paper.getMagazine() != null) {
        	paperIU.setNazivCasopisa(paper.getMagazine().getName());
        }
        if (paper.getDostupnost() != null) {
        	paperIU.setDostupnost(paper.getDostupnost());
        }
        if (paper.getCoauthors().size() > 0) {
        	for (Coauthor c : paper.getCoauthors()) {
            	paperIU.getAutori().add(new AutorIndexUnit(c.getFirstname(), c.getLastname()));
			}
        }
        return paperIU;
    }

    @Override
    public PaperIndexUnit getIUPaper(File file) {
        return null;
    }

    @Override
    public String getText(File file) {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.err.println("Error while converting document to pdf");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getText(Document document) {
        return null;
    }

}
