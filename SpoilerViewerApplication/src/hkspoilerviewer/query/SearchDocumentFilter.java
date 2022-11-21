package hkspoilerviewer.query;

public interface SearchDocumentFilter {
  boolean accept(SearchDocument searchDocument);
}
