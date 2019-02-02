package ftn.uns.ac.rs.naucnaCentrala.elasticSearch.handler;

import org.apache.lucene.document.Document;

import ftn.uns.ac.rs.naucnaCentrala.elasticSearch.model.PaperIndexUnit;
import ftn.uns.ac.rs.naucnaCentrala.model.Paper;

import java.io.File;
import java.nio.file.Path;

public abstract class DocumentHandler {

    public abstract PaperIndexUnit getIUPaper(Paper paper, Path filepath);
    public abstract PaperIndexUnit getIUPaper(File file);
    public abstract String getText(File file);
    public abstract String getText(Document document);
}
