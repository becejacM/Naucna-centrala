package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
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
        PaperIndexUnit ireBook = new PaperIndexUnit();

        
        //ireBook.setTitle(eBook.getName());
        ireBook.setFilename(paper.getFilename());
        File bookFile = filepath.toFile();
        ireBook.setText(getText(bookFile));


        if (paper.getKeywords() != null) {
            //ireBook.setKeywords(eBook.getKeywords());
            //ireBook.setNaucneOblasti(String.valueOf(eBook.getScientificField().getId()));
        }

        return ireBook;
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
