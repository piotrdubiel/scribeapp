package io.scribeapp.classifier.model;

public class ClassificationResult {
    private String word;

    public ClassificationResult() {}

    public ClassificationResult(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }
}
